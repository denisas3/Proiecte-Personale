package jsonprotocol;

import dto.EmployeeDTO;
import dto.ShowDTO;
import dto.TicketDTO;
import org.example.lab03.domain.Employee;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

public class Request {
    private RequestType type;
    private EmployeeDTO employee;
    private ShowDTO show;
    private TicketDTO ticket;
    private ShowDTO[] shows;
    private TicketDTO[] tickets;
    private LocalDateTime localDate;

    public Request() {}
    public RequestType getType() {return type;}
    public void setType(RequestType type) {this.type = type;}

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

    public LocalDateTime getLocalDate() {return localDate;}
    public void setLocalDate(LocalDateTime localDate) {this.localDate = localDate;}

    @Override
    public String toString() {
        return "Request{" +
                "type: " + type +
                ", employee: " + employee +
                ", show: " + show +
                ", ticket: " + ticket +
                ", shows: " + Arrays.toString(shows) +
                ", tickets: " + Arrays.toString(tickets) +
                "}";

    }
}
