package jsonprotocol;

import dto.DTOUtils;
import dto.ShowDTO;
import org.example.lab03.domain.Employee;
import org.example.lab03.domain.Show;
import org.example.lab03.domain.Ticket;

import java.time.LocalDateTime;

public class JsonProtocolUtils {

    public static Response createOkResponse(){
        Response resp=new Response();
        resp.setType(ResponseType.OK);
        return resp;
    }

    public static Response createErrorResponse(String errorMessage){
        Response resp=new Response();
        resp.setType(ResponseType.ERROR);
        resp.setErrorMessage(errorMessage);
        return resp;
    }

    public static Response createGetShowsResponse(Show[] shows){
        Response resp=new Response();
        resp.setType(ResponseType.GET_SHOWS);
        resp.setShows(DTOUtils.getShowDTOs(shows));
        return resp;
    }

    public static Response createGetShowsByDateResponse(Show[] shows){
        Response resp=new Response();
        resp.setType(ResponseType.GET_SHOWS_BY_DATE);
        resp.setShows(DTOUtils.getShowDTOs(shows));
        return resp;
    }

    public static Response createGetTicketsByShowResponse(Ticket[] tickets){
        Response resp=new Response();
        resp.setType(ResponseType.GET_TICKETS_BY_SHOW);
        resp.setTickets(DTOUtils.getTicketDTOs(tickets));
        return resp;
    }

    public static Response createTicketBoughtResponse(Ticket ticket){
        Response resp=new Response();
        resp.setType(ResponseType.TICKET_BOUGHT);
        resp.setTicket(DTOUtils.getTicketDTO(ticket));
        return resp;
    }

    public static Response createTicketUpdatedResponse(Ticket ticket){
        Response resp=new Response();
        resp.setType(ResponseType.TICKET_UPDATED);
        resp.setTicket(DTOUtils.getTicketDTO(ticket));
        return resp;
    }

    public static Request createLoginRequest(Employee employee){
        Request req=new Request();
        req.setType(RequestType.LOGIN);
        req.setEmployee(DTOUtils.getEmployeeDTO(employee));
        return req;
    }

    public static Request createLogoutRequest(Employee employee){
        Request req=new Request();
        req.setType(RequestType.LOGOUT);
        req.setEmployee(DTOUtils.getEmployeeDTO(employee));
        return req;
    }

    public static Request createShowsRequest(){
        Request req=new Request();
        req.setType(RequestType.GET_SHOWS);
        return req;
    }

    public static Request createShowsByDateRequest(LocalDateTime dateTime){
        Request req=new Request();
        req.setType(RequestType.GET_SHOWS_BY_DATE);
        req.setLocalDate(dateTime);
        return req;
    }

    public static Request createBuyTicketsRequest(Ticket ticket){
        Request req=new Request();
        req.setType(RequestType.BUY_TICKET);
        req.setTicket(DTOUtils.getTicketDTO(ticket));
        return req;
    }

    public static Request createUpdateTicketRequest(Ticket ticket){
        Request req=new Request();
        req.setType(RequestType.UPDATE_TICKET);
        req.setTicket(DTOUtils.getTicketDTO(ticket));
        return req;
    }

    public static Request createTicketsByShowRequest(Show show){
        Request req=new Request();
        req.setType(RequestType.GET_TICKETS_BY_SHOW);
        req.setShow(DTOUtils.getShowDTO(show));
        return req;
    }
}
