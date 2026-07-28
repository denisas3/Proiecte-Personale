package jsonprotocol;

import com.google.gson.Gson;
import dto.DTOUtils;
import dto.EmployeeDTO;
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
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ClientJsonWorker implements Runnable, ILabObserver {
    private ILabServices server;
    private Socket connection;

    private BufferedReader input;
    private PrintWriter output;
    private Gson gsonFormatter;
    private volatile boolean connected;

    private static Logger logger = LogManager.getLogger(ClientJsonWorker.class);

    public ClientJsonWorker(ILabServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        gsonFormatter = new com.google.gson.GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        try{
            output=new PrintWriter(connection.getOutputStream());
            input=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            connected=true;
        }catch(IOException e){
            logger.error(e);
            logger.error(e.getStackTrace());
        }
    }

    public  void run() {
        while(connected){
            try{
                String requestLine=input.readLine();
                Request request = gsonFormatter.fromJson(requestLine, Request.class);
                Response response = handleRequest(request);
                if(response!=null){
                    sendResponse(response);
                }
            }catch(IOException e){
                logger.error(e);
                logger.error(e.getStackTrace());
            }
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                logger.error(e);
                logger.error(e.getStackTrace());
            }
        }
        try{
            input.close();
            output.close();
            connection.close();
        }catch(IOException e){
            logger.error("Error "+e);
        }
    }

    /// metode pt functionalitatile mele
    ///

    @Override
    public void ticketsSold(Ticket ticket) throws LabException {
        Response response=JsonProtocolUtils.createTicketBoughtResponse(ticket);
        logger.debug("TicketBoughtResived "+ticket);
        try{
            sendResponse(response);
        }catch(IOException e){
            throw new LabException("TicketBoughtResived: "+e);
        }
    }

    @Override
    public void ticketsUpdated(Ticket ticket) throws LabException {
        Response response=JsonProtocolUtils.createTicketUpdatedResponse(ticket);
        logger.debug("TicketUpdatedResived "+ticket);
        try{
            sendResponse(response);
        }catch(IOException e){
            throw new LabException("TicketUpdatedResived: "+e);
        }
    }

    private static Response okResponse=JsonProtocolUtils.createOkResponse();

    private Response handleRequest(Request request){
        Response response=null;
        if(request.getType()== RequestType.LOGIN){
            logger.debug("Login request ...{}" + request.getEmployee());
            EmployeeDTO employeeDTO = request.getEmployee();
            Employee employee = DTOUtils.getFromEmployeeDTO(employeeDTO);
            employee.setPassword(employee.getPassword());
            try{
                server.login(employee, this);
                return okResponse;
            }catch(LabException e){
                connected =  false;
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }

        if(request.getType() == RequestType.LOGOUT){
            logger.debug("Logout request ...{}" + request.getEmployee());
            EmployeeDTO employeeDTO = request.getEmployee();
            Employee employee = DTOUtils.getFromEmployeeDTO(employeeDTO);
            try{
                server.logout(employee, this);
                connected = false;
                return okResponse;
            }catch(LabException e){
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }

        if(request.getType() == RequestType.GET_SHOWS){
            logger.debug("Get shows request ...{}" + request.getEmployee());
            try {
                Iterable<Show> showsIterable = server.getAllShows();
                Show[] shows = iterableToShowArray(showsIterable);
                return JsonProtocolUtils.createGetShowsResponse(shows);
            } catch (LabException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }

        if (request.getType() == RequestType.GET_SHOWS_BY_DATE) {
            logger.debug("Get shows by date request {}", request.getLocalDate());
            try {
                LocalDateTime date = request.getLocalDate();
                List<Show> shows = server.findShowsByDate(date);
                Show[] showArray = shows.toArray(new Show[0]);
                return JsonProtocolUtils.createGetShowsByDateResponse(showArray);
            } catch (LabException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }

        if (request.getType() == RequestType.BUY_TICKET) {
            logger.debug("Buy ticket request {}", request.getTicket());
            try {
                Ticket ticket = DTOUtils.getFromTicketDTO(request.getTicket());
                server.buyTicket(ticket);
                return okResponse;
            } catch (LabException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }

        if (request.getType() == RequestType.UPDATE_TICKET) {
            logger.debug("Update ticket request {}", request.getTicket());
            try {
                Ticket ticket = DTOUtils.getFromTicketDTO(request.getTicket());
                server.updateTicket(ticket);
                return okResponse;
            } catch (LabException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }

        if (request.getType() == RequestType.GET_TICKETS_BY_SHOW) {
            logger.debug("Get tickets by show request {}", request.getShow());
            try {
                Show show = DTOUtils.getFromShowDTO(request.getShow());
                List<Ticket> tickets = server.findTicketsByShow(show);
                Ticket[] ticketArray = tickets.toArray(new Ticket[0]);
                return JsonProtocolUtils.createGetTicketsByShowResponse(ticketArray);
            } catch (LabException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }

        return response;

    }

    private Show[] iterableToShowArray(Iterable<Show> iterable) {
        List<Show> list = new ArrayList<>();
        for (Show show : iterable) {
            list.add(show);
        }
        return list.toArray(new Show[0]);
    }

    private void sendResponse(Response response) throws IOException{
        String responseLine=gsonFormatter.toJson(response);
        logger.debug("sending response "+responseLine);
        synchronized (output) {
            output.println(responseLine);
            output.flush();
        }
    }
}
