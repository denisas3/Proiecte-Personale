package server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.lab03.domain.Employee;
import org.example.lab03.domain.Show;
import org.example.lab03.domain.Ticket;
import org.example.lab03.repository.EmployeeDbHibernateRepository;
import org.example.lab03.repository.ShowDbHibernateRepository;
import org.example.lab03.repository.TicketDbHibernateRepository;
import org.example.lab03.services.ILabObserver;
import org.example.lab03.services.ILabServices;
import org.example.lab03.services.LabException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServicesImplHibernate implements ILabServices {

    private EmployeeDbHibernateRepository employeeDbRepository;
    private ShowDbHibernateRepository showDbRepository;
    private TicketDbHibernateRepository ticketDbRepository;

    private Map<String, ILabObserver> loggedClients;

    private static Logger logger = LogManager.getLogger(ServicesImplHibernate.class);

    public ServicesImplHibernate(EmployeeDbHibernateRepository employeeDbRepository,
                                 ShowDbHibernateRepository showDbRepository,
                                 TicketDbHibernateRepository ticketDbRepository) {
        this.employeeDbRepository = employeeDbRepository;
        this.showDbRepository = showDbRepository;
        this.ticketDbRepository = ticketDbRepository;
        this.loggedClients = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void login(Employee employee, ILabObserver client) throws LabException {
        logger.info("Login attempt for user {}", employee.getUsername());

        Employee employeeFromDb = employeeDbRepository.findByUsernameAndPassword(
                employee.getUsername(),
                employee.getPassword()
        );

        logger.info("Employee from db = {}", employeeFromDb);

        if (employeeFromDb == null) {
            throw new LabException("Authentication failed.");
        }

        if (loggedClients.get(employeeFromDb.getUsername()) != null) {
            throw new LabException("Employee already logged in.");
        }

        loggedClients.put(employeeFromDb.getUsername(), client);
        logger.info("Login success for user {}", employeeFromDb.getUsername());
    }

    @Override
    public synchronized void logout(Employee employee, ILabObserver client) throws LabException {
        ILabObserver localClient = loggedClients.remove(employee.getUsername());

        if (localClient == null) {
            throw new LabException("Employee " + employee.getUsername() + " is not logged in");
        }
    }

    @Override
    public Iterable<Show> getAllShows() throws LabException {
        logger.info("Getting all shows");
        return showDbRepository.findAll();
    }

    @Override
    public List<Show> findShowsByDate(LocalDateTime date) throws LabException {
        logger.info("Finding shows by date {}", date);
        return showDbRepository.findByDate(date);
    }

    @Override
    public List<Ticket> findTicketsByShow(Show show) throws LabException {
        logger.info("Finding tickets for show {}", show);
        return ticketDbRepository.findByShow(show);
    }

    @Override
    public synchronized void buyTicket(Ticket ticket) throws LabException {
        logger.info("Buying ticket {}", ticket);

        Show show = showDbRepository.findOne(ticket.getShow().getId());

        if (show == null) {
            throw new LabException("Show not found.");
        }

        int requestedSeats = ticket.getSeatCount();

        if (requestedSeats <= 0) {
            throw new LabException("Seat count must be positive.");
        }

        if (show.getAvailableSeats() < requestedSeats) {
            throw new LabException("Not enough available seats.");
        }

        ticket.setShow(show);

        Ticket result = ticketDbRepository.save(ticket);

        if (result == null) {
            throw new LabException("Ticket could not be saved.");
        }

        show.setAvailableSeats(show.getAvailableSeats() - requestedSeats);
        show.setSoldSeats(show.getSoldSeats() + requestedSeats);

        Show updatedShow = showDbRepository.update(show);

        if (updatedShow == null) {
            throw new LabException("Show could not be updated.");
        }

        notifyTicketSold(ticket);
    }

    @Override
    public synchronized void updateTicket(Ticket ticket) throws LabException {
        logger.info("Updating ticket {}", ticket);

        Ticket oldTicket = ticketDbRepository.findOne(ticket.getId());

        if (oldTicket == null) {
            throw new LabException("Ticket not found.");
        }

        Show show = showDbRepository.findOne(ticket.getShow().getId());

        if (show == null) {
            throw new LabException("Show not found.");
        }

        int oldSeats = oldTicket.getSeatCount();
        int newSeats = ticket.getSeatCount();
        int diff = newSeats - oldSeats;

        if (newSeats <= 0) {
            throw new LabException("Seat count must be positive.");
        }

        if (diff > 0 && show.getAvailableSeats() < diff) {
            throw new LabException("Not enough available seats for update.");
        }

        ticket.setShow(show);

        Ticket result = ticketDbRepository.update(ticket);

        if (result == null) {
            throw new LabException("Ticket could not be updated.");
        }

        show.setAvailableSeats(show.getAvailableSeats() - diff);
        show.setSoldSeats(show.getSoldSeats() + diff);

        Show updatedShow = showDbRepository.update(show);

        if (updatedShow == null) {
            throw new LabException("Show could not be updated.");
        }

        notifyTicketUpdated(ticket);
    }

    private void notifyTicketSold(Ticket ticket) {
        logger.info("Notifying logged clients about sold ticket");

        for (ILabObserver client : loggedClients.values()) {
            try {
                client.ticketsSold(ticket);
            } catch (LabException e) {
                logger.error("Error notifying client about sold ticket", e);
            }
        }
    }

    private void notifyTicketUpdated(Ticket ticket) {
        logger.info("Notifying logged clients about updated ticket");

        for (ILabObserver client : loggedClients.values()) {
            try {
                client.ticketsUpdated(ticket);
            } catch (LabException e) {
                logger.error("Error notifying client about updated ticket", e);
            }
        }
    }

    private final int defaultThreadsNo = 3;
}