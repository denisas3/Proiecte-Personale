package org.example.lab03.services;

import org.example.lab03.Show;
import org.example.lab03.Ticket;

import java.util.List;

public interface ITicketService {
    void buyTicket(Integer showId, String buyerName, Integer seatCount) throws Exception;
    void updateTicket(Integer ticketId, Integer newSeatCount) throws Exception;
    Ticket findTicket(Integer id);
    List<Ticket> findByShow(Show show) throws Exception;
}
