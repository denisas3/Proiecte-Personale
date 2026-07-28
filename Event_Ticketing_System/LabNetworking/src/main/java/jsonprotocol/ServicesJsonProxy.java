package jsonprotocol;

import com.google.gson.Gson;
import dto.DTOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.lab03.domain.Employee;
import org.example.lab03.domain.Show;
import org.example.lab03.domain.Ticket;
import org.example.lab03.services.ILabObserver;
import org.example.lab03.services.ILabServices;
import org.example.lab03.services.LabException;
import com.google.gson.GsonBuilder;
import java.time.LocalDateTime;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServicesJsonProxy implements ILabServices {
    private String host;
    private int port;

    private ILabObserver client;

    private BufferedReader input;
    private PrintWriter output;
    private Gson gsonFormatter;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    private static Logger logger = LogManager.getLogger(ServicesJsonProxy.class);

    public ServicesJsonProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<Response>();
    }

    @Override
    public void login(Employee employee, ILabObserver client) throws LabException {
        initializeConnection();
        employee.setPassword(employee.getPassword());
        Request req = JsonProtocolUtils.createLoginRequest(employee);
        sendRequest(req);
        Response response = readResponse();
        if(response.getType() == ResponseType.OK){
            this.client = client;
            return;
        }
        if(response.getType() == ResponseType.ERROR){
            String err =response.getErrorMessage();
            closeConnection();
            throw new LabException(err);
        }
    }

    @Override
    public void logout(Employee employee, ILabObserver client) throws LabException {
        Request req=JsonProtocolUtils.createLogoutRequest(employee);
        sendRequest(req);
        Response response=readResponse();
        closeConnection();
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();//data().toString();
            throw new LabException(err);
        }
    }

    @Override
    public Iterable<Show> getAllShows() throws LabException {
        Request req = JsonProtocolUtils.createShowsRequest();
        sendRequest(req);
        Response response = readResponse();

        if (response.getType() == ResponseType.ERROR) {
            throw new LabException(response.getErrorMessage());
        }

        if (response.getType() == ResponseType.GET_SHOWS) {
            return List.of(DTOUtils.getFromShowDTOs(response.getShows()));
        }

        throw new LabException("Unexpected response type: " + response.getType());
    }

    @Override
    public List<Show> findShowsByDate(LocalDateTime date) throws LabException {
        Request req = JsonProtocolUtils.createShowsByDateRequest(date);
        sendRequest(req);
        Response response = readResponse();

        if (response.getType() == ResponseType.ERROR) {
            throw new LabException(response.getErrorMessage());
        }

        if (response.getType() == ResponseType.GET_SHOWS_BY_DATE) {
            return List.of(DTOUtils.getFromShowDTOs(response.getShows()));
        }

        throw new LabException("Unexpected response type: " + response.getType());
    }

    @Override
    public List<Ticket> findTicketsByShow(Show show) throws LabException {
        Request req = JsonProtocolUtils.createTicketsByShowRequest(show);
        sendRequest(req);
        Response response = readResponse();

        if (response.getType() == ResponseType.ERROR) {
            throw new LabException(response.getErrorMessage());
        }

        if (response.getType() == ResponseType.GET_TICKETS_BY_SHOW) {
            return List.of(DTOUtils.getFromTicketDTOs(response.getTickets()));
        }

        throw new LabException("Unexpected response type: " + response.getType());
    }

    @Override
    public void buyTicket(Ticket ticket) throws LabException{
        Request req = JsonProtocolUtils.createBuyTicketsRequest(ticket);
        sendRequest(req);
        Response response = readResponse();

        if (response.getType() == ResponseType.ERROR) {
            throw new LabException(response.getErrorMessage());
        }

        if (response.getType() != ResponseType.OK) {
            throw new LabException("Unexpected response type: " + response.getType());
        }
    }

    @Override
    public void updateTicket(Ticket ticket) throws LabException {
        Request req = JsonProtocolUtils.createUpdateTicketRequest(ticket);
        sendRequest(req);
        Response response = readResponse();

        if (response.getType() == ResponseType.ERROR) {
            throw new LabException(response.getErrorMessage());
        }

        if (response.getType() != ResponseType.OK) {
            throw new LabException("Unexpected response type: " + response.getType());
        }
    }

    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            logger.error(e);
            logger.error(e.getStackTrace());
        }
    }

    private void sendRequest(Request request)throws LabException {
        String reqLine=gsonFormatter.toJson(request);
        try {
            output.println(reqLine);
            output.flush();
        } catch (Exception e) {
            throw new LabException("Error sending object "+e);
        }
    }

    private Response readResponse() throws LabException {
        Response response=null;
        try{

            response=qresponses.take();

        } catch (InterruptedException e) {
            logger.error(e);
            logger.error(e.getStackTrace());
        }
        return response;
    }

    private void initializeConnection() throws LabException {
        try {
            gsonFormatter = new com.google.gson.GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .create();
            connection=new Socket(host,port);
            output=new PrintWriter(connection.getOutputStream());
            output.flush();
            input=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            finished=false;
            startReader();
        } catch (IOException e) {
            logger.error(e);
            logger.error(e.getStackTrace());
        }
    }
    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }

    private void handleUpdate(Response response){
        if(response.getType() == ResponseType.TICKET_BOUGHT){
            Ticket ticket= DTOUtils.getFromTicketDTO(response.getTicket());
            logger.debug("Ticket bought: "+ticket);
            try{
                client.ticketsSold(ticket);
            }catch (LabException e){
                logger.error(e);
                logger.error(e.getStackTrace());
            }
        }

        if(response.getType() == ResponseType.TICKET_UPDATED){
            Ticket ticket= DTOUtils.getFromTicketDTO(response.getTicket());
            logger.debug("Ticket updated: "+ticket);
            try{
                client.ticketsUpdated(ticket);
            }catch (LabException e){
                logger.error(e);
                logger.error(e.getStackTrace());
            }
        }
    }

    private boolean isUpdate(Response response){
        return response.getType()== ResponseType.TICKET_BOUGHT || response.getType()== ResponseType.TICKET_UPDATED;
    }
    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    String responseLine=input.readLine();
                    logger.debug("response received {}",responseLine);
                    if (responseLine == null) {
                        finished = true;
                        break;
                    }

                    Response response=gsonFormatter.fromJson(responseLine, Response.class);
                    if (response == null) {
                        continue;
                    }
                    if (isUpdate(response)){
                        handleUpdate(response);
                    }else{

                        try {
                            qresponses.put(response);
                        } catch (InterruptedException e) {
                            logger.error(e);
                            logger.error(e.getStackTrace());
                        }
                    }
                } catch (IOException e) {
                    logger.error("Reading error "+e);
                }
            }
        }
    }

}
