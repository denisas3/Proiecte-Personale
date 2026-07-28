package dto;

import org.example.lab03.domain.Employee;
import org.example.lab03.domain.Show;
import org.example.lab03.domain.Ticket;

import java.time.LocalDateTime;

public class DTOUtils {
    public static Employee getFromEmployeeDTO(EmployeeDTO employeeDTO) {
        Long id =employeeDTO.getId();
        String username =employeeDTO.getUsername();
        String password =employeeDTO.getPassword();
        String name =employeeDTO.getName();
        Employee employee = new Employee(username,password,name);
        employee.setId(id);
        return employee;
    }

    public static EmployeeDTO getEmployeeDTO(Employee employee){
        Long id = employee.getId();
        String username =employee.getUsername();
        String password =employee.getPassword();
        String name =employee.getName();
        return new EmployeeDTO(id, username, password, name);
    }

    public static Show getFromShowDTO(ShowDTO showDTO) {
        Long id = showDTO.getId();
        String artistName =showDTO.getArtistName();
        LocalDateTime date =showDTO.getDate();
        String location =showDTO.getLocation();
        Integer availableSeats = showDTO.getAvailableSeats();
        Integer soldSeats =  showDTO.getSoldSeats();
        Show show = new Show(artistName,date,location,availableSeats,soldSeats);
        show.setId(id);
        return show;
    }

    public static ShowDTO getShowDTO(Show show){
        Long id = show.getId();
        String artistName =show.getArtistName();
        LocalDateTime date =show.getDate();
        String location =show.getLocation();
        Integer availableSeats = show.getAvailableSeats();
        Integer soldSeats =  show.getSoldSeats();
        return new ShowDTO(id,artistName,date,location,availableSeats,soldSeats);
    }

    public static Ticket getFromTicketDTO(TicketDTO ticketDTO) {
        Long id = ticketDTO.getId();
        String buyerName = ticketDTO.getBuyerName();
        Integer seatCount =  ticketDTO.getSeatCount();
        LocalDateTime soldAt = ticketDTO.getSoldAt();
        Show show = ticketDTO.getShow();
        Ticket ticket = new Ticket(buyerName,seatCount,soldAt,show);
        ticket.setId(id);
        return ticket;
    }

    public static TicketDTO getTicketDTO(Ticket ticket){
        Long id = ticket.getId();
        String buyerName =ticket.getBuyerName();
        Integer seatCount =  ticket.getSeatCount();
        LocalDateTime soldAt = ticket.getSoldAt();
        Show show = ticket.getShow();
        return new TicketDTO(id,buyerName,seatCount,soldAt,show);
    }

    public static Show[] getFromShowDTOs(ShowDTO[] shows){
        Show[] showArray = new Show[shows.length];
        for (int i = 0; i < shows.length; i++) {
            showArray[i] = getFromShowDTO(shows[i]);
        }
        return showArray;
    }

    public static ShowDTO[] getShowDTOs(Show[] show){
        ShowDTO[] showDTOArray = new ShowDTO[show.length];
        for (int i = 0; i < show.length; i++) {
            showDTOArray[i] = getShowDTO(show[i]);
        }
        return showDTOArray;
    }

    public static Ticket[] getFromTicketDTOs(TicketDTO[] ticketDTOs){
        Ticket[] ticketArray = new Ticket[ticketDTOs.length];
        for (int i = 0; i < ticketDTOs.length; i++) {
            ticketArray[i] = getFromTicketDTO(ticketDTOs[i]);
        }
        return ticketArray;
    }

    public static TicketDTO[] getTicketDTOs(Ticket[] tickets){
        TicketDTO[] ticketDTOArray = new TicketDTO[tickets.length];
        for (int i = 0; i < tickets.length; i++) {
            ticketDTOArray[i] = getTicketDTO(tickets[i]);
        }
        return ticketDTOArray;
    }


}
