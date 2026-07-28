package org.example.lab03.repository;

import org.example.lab03.domain.Show;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
public class ShowDbHibernateRepository implements ShowRepository {

    @Override
    public Show save(Show show) {
        HibernateUtils.getSessionFactory()
                .inTransaction(session -> {
                        Long maxId = session
                        .createQuery("select coalesce(max(s.id), 0) from Show s", Long.class)
                        .getSingleResult();

                    show.setId(maxId + 1);

        session.merge(show);
    });

        return show;
    }

    @Override
    public Show findOne(Long id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery(
                            "from Show where id = :id",
                            Show.class
                    )
                    .setParameter("id", id)
                    .getSingleResultOrNull();
        }
    }

    @Override
    public List<Show> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery(
                    "from Show",
                    Show.class
            ).getResultList();
        }
    }

    @Override
    public Show update(Show show) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            if (!Objects.isNull(session.find(Show.class, show.getId()))) {
                System.out.println("In update, am gasit show-ul cu id-ul " + show.getId());
                session.merge(show);
                session.flush();
            }
        });

        return show;
    }

    @Override
    public Show delete(Long id) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Show show = session.find(Show.class, id);

            if (show != null) {
                session.remove(show);
                session.flush();
            }
        });

        return null;
    }

    @Override
    public List<Show> findByArtist(String artist) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery(
                            "from Show s where s.artistName = :artist",
                            Show.class
                    )
                    .setParameter("artist", artist)
                    .getResultList();
        }
    }

    @Override
    public List<Show> findByDate(LocalDateTime date) {
        LocalDateTime start = date.toLocalDate().atStartOfDay();
        LocalDateTime end = date.toLocalDate().atTime(23, 59, 59);

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery(
                            "from Show s where s.date >= :start and s.date <= :end",
                            Show.class
                    )
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getResultList();
        }
    }
}