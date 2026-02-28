package curs3.io.repository;

import curs3.io.domain.Event;
import curs3.io.domain.RaceEvent;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class EventRepository {
    private final Connection conn;

    public void save(Event event) {
        try{

            var stmt = conn.prepareStatement(
                    "INSERT INTO events(nume) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            stmt.setString(1, event.getNumeEvent());
            stmt.executeUpdate();

            var rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                long newID = rs.getLong(1);
                event.setId(newID);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Cannot save event: " + e.getMessage());
        }
    }

    public void delete(Long id) {
        try{
            var stmt = conn.prepareStatement(
                    "DELETE FROM events WHERE eventID = ?"
            );

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Cannot delete event: " + e.getMessage());
        }
    }

    public List<Event> findAll() {
        List<Event> list = new ArrayList<>();

        try{

            var stmt = conn.prepareStatement("SELECT * FROM events");
            var rs = stmt.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("eventID");
                String nume = rs.getString("nume");

                Event e = new RaceEvent(id, nume);
                list.add(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Cannot load events: " + e.getMessage());
        }

        return list;
    }
}
