package org.example.lab03.repository;

import org.example.lab03.domain.Show;
import org.example.lab03.domain.Ticket;
import org.hibernate.Session;

import java.util.List;
import java.util.Objects;

public class TicketDbHibernateRepository implements TicketRepository {

    @Override
    public Ticket save(Ticket ticket) {
        HibernateUtils.getSessionFactory()
                .inTransaction(session -> session.persist(ticket));

        return ticket;
    }

    @Override
    public Ticket findOne(Long id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery(
                            "from Ticket t join fetch t.show where t.id = :id",
                            Ticket.class
                    )
                    .setParameter("id", id)
                    .getSingleResultOrNull();
        }
    }

    @Override
    public List<Ticket> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery(
                    "from Ticket t join fetch t.show",
                    Ticket.class
            ).getResultList();
        }
    }

    @Override
    public Ticket update(Ticket ticket) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            if (!Objects.isNull(session.find(Ticket.class, ticket.getId()))) {
                System.out.println("In update, am gasit ticket-ul cu id-ul " + ticket.getId());
                session.merge(ticket);
                session.flush();
            }
        });

        return ticket;
    }

    @Override
    public Ticket delete(Long id) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Ticket ticket = session.find(Ticket.class, id);

            if (ticket != null) {
                session.remove(ticket);
                session.flush();
            }
        });

        return null;
    }

    @Override
    public List<Ticket> findByShow(Show show) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery(
                            "from Ticket t join fetch t.show where t.show.id = :showId",
                            Ticket.class
                    )
                    .setParameter("showId", show.getId())
                    .getResultList();
        }
    }
}