package org.example.lab03.services;

import org.example.lab03.Show;
import org.example.lab03.Ticket;
import org.example.lab03.repository.ShowRepository;
import org.example.lab03.repository.TicketRepository;

import java.time.LocalDateTime;
import java.util.List;

public class TicketService implements ITicketService {
    private final TicketRepository ticketRepository;
    private final ShowRepository showRepository;

    public TicketService(TicketRepository ticketRepository, ShowRepository showRepository) {
        this.ticketRepository = ticketRepository;
        this.showRepository = showRepository;
    }

    @Override
    public void buyTicket(Integer showId, String buyerName, Integer seatCount) throws Exception{
        if (buyerName == null || buyerName.isBlank()) {
            throw new Exception("Buyer name cannot be null or empty  ");
        }

        if (seatCount == null || seatCount <= 0) {
            throw new Exception("Seat count cannot be zero");
        }

        Show show = showRepository.findOne(showId);
        if (show == null) {
            throw new Exception("Show not found");
        }

        if (show.getAvailableSeats() < seatCount) {
            throw new Exception("Not enough available seats");
        }

        Ticket ticket = new Ticket(buyerName,seatCount, LocalDateTime.now(),show);
        ticketRepository.save(ticket);

        show.setSoldSeats(show.getSoldSeats() + seatCount);
        show.setAvailableSeats(show.getAvailableSeats() - seatCount);
        showRepository.update(show);
    }


    @Override
    public void updateTicket(Integer ticketId, Integer newSeatCount) throws Exception {
        if (newSeatCount == null || newSeatCount <= 0) {
            throw new Exception("Seat count cannot be zero");
        }

        Ticket ticket = ticketRepository.findOne(ticketId);
        if (ticket == null) {
            throw new Exception("Ticket not found");
        }

        Show show = showRepository.findOne(ticket.getShow().getId());
        if (show == null) {
            throw new Exception("Show not found");
        }

        int oldSeatCount = ticket.getSeatCount();
        int difference = newSeatCount - oldSeatCount;

        if (difference > 0) {
            // cresti nr de locuri
            if (show.getAvailableSeats() < difference) {
                throw new Exception("Not enough available seats");
            }

            show.setAvailableSeats(show.getAvailableSeats() - difference);
            show.setSoldSeats(show.getSoldSeats() + difference);

        } else if (difference < 0) {
            // scazi nr de locuri
            int returnedSeats = -difference;

            show.setAvailableSeats(show.getAvailableSeats() + returnedSeats);
            show.setSoldSeats(show.getSoldSeats() - returnedSeats);
        } else {
            return;
        }

        ticket.setSeatCount(newSeatCount);
        ticket.setSoldAt(LocalDateTime.now());

        ticketRepository.update(ticket);
        showRepository.update(show);
    }

    public void updateTicketByBuyerName(String buyerName, int newSeats) {
        Iterable<Ticket> tickets = ticketRepository.findAll();

        Ticket foundTicket = null;
        for (Ticket ticket : tickets) {
            if (ticket.getBuyerName().equalsIgnoreCase(buyerName)) {
                foundTicket = ticket;
                break;
            }
        }

        if (foundTicket == null) {
            throw new RuntimeException("Nu exista niciun bilet pentru acest cumparator.");
        }

        foundTicket.setSeatCount(newSeats);
        ticketRepository.update(foundTicket);
    }

    @Override
    public Ticket findTicket(Integer id) {
        return ticketRepository.findOne(id);
    }

    @Override
    public List<Ticket> findByShow(Show show) {
        return ticketRepository.findByShow(show);
    }
}
