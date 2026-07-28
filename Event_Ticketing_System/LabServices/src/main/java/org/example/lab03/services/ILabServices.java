package org.example.lab03.services;

import org.example.lab03.domain.Employee;
import org.example.lab03.domain.Show;
import org.example.lab03.domain.Ticket;

import java.time.LocalDateTime;
import java.util.List;

public interface ILabServices {
    void login(Employee employee, ILabObserver client) throws LabException;
    void logout(Employee employee, ILabObserver client) throws LabException;

    Iterable<Show> getAllShows() throws LabException;
    List<Show> findShowsByDate(LocalDateTime date) throws LabException;
    List<Ticket> findTicketsByShow(Show show) throws LabException;

    void buyTicket(Ticket ticket) throws LabException;
    void updateTicket(Ticket ticket) throws LabException;
}
