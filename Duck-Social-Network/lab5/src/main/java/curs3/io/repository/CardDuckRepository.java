package curs3.io.repository;

import curs3.io.domain.Duck;
import curs3.io.domain.DuckType;
import curs3.io.domain.FlyingDuck;
import curs3.io.domain.SwimmingDuck;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CardDuckRepository {
    private final Connection conn;

    public void addDuckToCard(Long cardID, Long duckID) {
        try{

            var stmt = conn.prepareStatement(
                    "INSERT INTO card_ducks(cardID, duckID) VALUES (?, ?)"
            );
            stmt.setLong(1, cardID);
            stmt.setLong(2, duckID);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Cannot add duck to card: " + e.getMessage());
        }
    }

    public void removeDuckFromCard(Long cardID, Long duckID) {
        try{

            var stmt = conn.prepareStatement(
                    "DELETE FROM card_ducks WHERE cardID = ? AND duckID = ?"
            );
            stmt.setLong(1, cardID);
            stmt.setLong(2, duckID);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Cannot remove duck from card: " + e.getMessage());
        }
    }

    public void deleteAllDucksFromCard(Long cardID) {
        try{

            var stmt = conn.prepareStatement(
                    "DELETE FROM card_ducks WHERE cardID = ?"
            );
            stmt.setLong(1, cardID);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Cannot delete ducks from card: " + e.getMessage());
        }
    }

    public List<Duck> findDucksForCard(Long cardID) {

        List<Duck> ducks = new ArrayList<>();

        try{

            var stmt = conn.prepareStatement(
                    "SELECT d.duckID, d.speed, d.resistance, d.type, d.userID, " +
                            "u.username, u.email, u.password " +
                            "FROM ducks d " +
                            "JOIN card_ducks cd ON d.duckID = cd.duckID " +
                            "JOIN users u ON d.userID = u.userID " +
                            "WHERE cd.cardID = ?"
            );

            stmt.setLong(1, cardID);

            var rs = stmt.executeQuery();

            while (rs.next()) {

                long duckID = rs.getLong("duckID");
                long userID = rs.getLong("userID");
                String username = rs.getString("username");
                String email = rs.getString("email");
                String password = rs.getString("password");
                double speed = rs.getDouble("speed");
                double resistance = rs.getDouble("resistance");

                DuckType type = DuckType.valueOf(rs.getString("type").toUpperCase());

                Duck duck;

                switch (type) {
                    case FLYING -> duck = new FlyingDuck(
                            duckID, userID, username, email, password,
                            speed, resistance, DuckType.FLYING
                    );
                    case SWIMMING -> duck = new SwimmingDuck(
                            duckID, userID, username, email, password,
                            speed, resistance, DuckType.SWIMMING
                    );
                    default -> throw new RuntimeException("Unknown duck type: " + type);
                }

                ducks.add(duck);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Cannot load card ducks: " + e.getMessage());
        }

        return ducks;
    }

}