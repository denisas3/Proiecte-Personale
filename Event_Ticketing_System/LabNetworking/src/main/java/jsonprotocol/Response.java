package jsonprotocol;

import dto.EmployeeDTO;
import dto.ShowDTO;
import dto.TicketDTO;

import java.util.Arrays;

public class Response {
    private ResponseType type;
    private String errorMessage;
    private EmployeeDTO employee;
    private ShowDTO show;
    private TicketDTO ticket;
    private ShowDTO[] shows;
    private TicketDTO[] tickets;

    public Response() {}
    public ResponseType getType() {return type;}
    public void setType(ResponseType type) {this.type = type;}

    public String getErrorMessage() {return errorMessage;}
    public void setErrorMessage(String errorMessage) {this.errorMessage = errorMessage;}

    public EmployeeDTO getEmployee() {return employee;}
    public void setEmployee(EmployeeDTO employee) {this.employee = employee;}

    public ShowDTO getShow() {return show;}
    public void setShow(ShowDTO show) {this.show = show;}

    public TicketDTO getTicket() {return ticket;}
    public void setTicket(TicketDTO ticket) {this.ticket = ticket;}

    public ShowDTO[] getShows() {return shows;}
    public void setShows(ShowDTO[] shows) {this.shows = shows;}

    public TicketDTO[] getTickets() {return tickets;}
    public void setTickets(TicketDTO[] tickets) {this.tickets = tickets;}

    @Override
    public String toString() {
        return "Response{" +
                "type: " + type +
                ", errorMessage: " + errorMessage +
                ", employee: " + employee +
                ", show: " + show +
                ", ticket: " + ticket +
                ", shows: " + Arrays.toString(shows) +
                ", tickets: " + Arrays.toString(tickets) +
                "}";

    }
}
