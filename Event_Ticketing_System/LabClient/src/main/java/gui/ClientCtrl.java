package gui;

import org.example.lab03.domain.Employee;
import org.example.lab03.domain.Show;
import org.example.lab03.domain.Ticket;
import org.example.lab03.services.ILabObserver;
import org.example.lab03.services.ILabServices;
import org.example.lab03.services.LabException;

import java.time.LocalDateTime;
import java.util.List;

public class ClientCtrl implements ILabObserver {

    private ILabServices server;
    private Employee currentEmployee;

    public ClientCtrl(ILabServices server) {
        this.server = server;
    }

    public void login(String username, String password) throws LabException {
        Employee employee = new Employee(username, password, "");
        server.login(employee, this);
        currentEmployee = employee;
    }

    public void logout() {
        try {
            server.logout(currentEmployee, this);
        } catch (LabException e) {
            System.out.println("Logout error " + e);
        }
    }

    public Iterable<Show> getAllShows() throws LabException {
        return server.getAllShows();
    }

    public List<Show> findShowsByDate(LocalDateTime date) throws LabException {
        return server.findShowsByDate(date);
    }

    public List<Ticket> findTicketsByShow(Show show) throws LabException {
        return server.findTicketsByShow(show);
    }

    public void buyTicket(Ticket ticket) throws LabException {
        server.buyTicket(ticket);
    }

    public void updateTicket(Ticket ticket) throws LabException {
        server.updateTicket(ticket);
    }

    @Override
    public void ticketsSold(Ticket ticket) throws LabException {
        // aici actualizez UI
    }

    @Override
    public void ticketsUpdated(Ticket ticket) throws LabException {
        // aici actualizez UI
    }
}
