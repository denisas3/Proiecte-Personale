package org.example.lab03.repository;

import org.example.lab03.domain.Show;
import org.example.lab03.domain.Ticket;

import java.util.List;

public interface TicketRepository extends Repository<Long, Ticket> {

    List<Ticket> findByShow(Show show);

    Ticket delete(Long integer);
}
