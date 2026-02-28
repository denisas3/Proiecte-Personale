package curs3.io.repository;

import curs3.io.domain.*;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class EventSubscribersRepository {
    private final Connection conn;

    public void addSubscriber(Long eventID, Long userID) {
        try{

            var stmt = conn.prepareStatement(
                    "INSERT INTO event_subscribers(eventID, userID) VALUES (?, ?)"
            );

            stmt.setLong(1, eventID);
            stmt.setLong(2, userID);
            stmt.executeUpdate();

        } catch (SQLException e) {

            if (e.getMessage().contains("duplicate key")) {
                throw new RuntimeException("Acest utilizator este deja abonat la acest event!");
            }

            throw new RuntimeException("Cannot add subscriber: " + e.getMessage());
        }
    }


    public void removeSubscriber(Long eventID, Long userID) {
        try{

            var stmt = conn.prepareStatement(
                    "DELETE FROM event_subscribers WHERE eventID = ? AND userID = ?"
            );

            stmt.setLong(1, eventID);
            stmt.setLong(2, userID);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Cannot remove subscriber: " + e.getMessage());
        }
    }

    public List<Utilizator> getSubscribers(Long eventID) {

        List<Utilizator> subs = new ArrayList<>();

        try{

            var stmt = conn.prepareStatement(
                    "SELECT u.userID, u.username, u.email, u.password, " +
                            "d.duckID, d.speed, d.resistance, d.type, " +
                            "p.persoanaID, p.lastname, p.firstname, p.jobTitle, p.birthDate, p.empatylevel " +
                            "FROM event_subscribers es " +
                            "JOIN users u ON es.userID = u.userID " +
                            "LEFT JOIN ducks d ON u.userID = d.userID " +
                            "LEFT JOIN persoane p ON u.userID = p.userID " +
                            "WHERE es.eventID = ?"
            );


            stmt.setLong(1, eventID);
            var rs = stmt.executeQuery();

            while (rs.next()) {

                long userID = rs.getLong("userID");
                String username = rs.getString("username");
                String email = rs.getString("email");
                String password = rs.getString("password");

                // =============================
                // 1️⃣ DETECTEAZĂ DACĂ ESTE RAȚĂ
                // =============================
                Integer duckIdRaw = rs.getInt("duckid");
                Long duckID = rs.wasNull() ? null : duckIdRaw.longValue();

                if (duckID != null) {
                    double speed = rs.getDouble("speed");
                    double resistance = rs.getDouble("resistance");
                    DuckType type = DuckType.valueOf(rs.getString("type").toUpperCase());

                    Duck duck = switch (type) {
                        case FLYING -> new FlyingDuck(
                                duckID, userID, username, email, password,
                                speed, resistance, DuckType.FLYING
                        );
                        case SWIMMING -> new SwimmingDuck(
                                duckID, userID, username, email, password,
                                speed, resistance, DuckType.SWIMMING
                        );
                        case FLYING_AND_SWIMMING -> new HybridDuck(
                                duckID, userID, username, email, password,
                                speed, resistance, DuckType.FLYING_AND_SWIMMING
                        );
                    };

                    subs.add(duck);
                    continue;
                }

                Integer persIdRaw = rs.getInt("persoanaid");
                Long persoanaID = rs.wasNull() ? null : persIdRaw.longValue();

                if (persoanaID != null) {
                    String ln = rs.getString("lastname");
                    String fn = rs.getString("firstname");
                    String jt = rs.getString("jobTitle");
                    var bd = rs.getDate("birthDate").toLocalDate();
                    Integer emp = rs.getInt("empatylevel");
                    if (rs.wasNull()) emp = null;

                    Persoana pers = new Persoana(
                            persoanaID,
                            userID,
                            username,
                            email,
                            password,
                            ln,
                            fn,
                            jt,
                            bd,
                            emp
                    );

                    subs.add(pers);
                    continue;
                }

                Utilizator u = new Utilizator(userID, username, email, password);
                subs.add(u);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Cannot load subscribers: " + e.getMessage());
        }

        return subs;
    }

}
