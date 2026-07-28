package org.example.lab03.services;

import org.example.lab03.domain.Show;
import org.example.lab03.domain.Ticket;

public interface    ILabObserver {
    void ticketsSold(Ticket ticket) throws LabException;
    void ticketsUpdated(Ticket ticket) throws LabException;
}
