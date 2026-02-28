package curs3.io.repository;

import curs3.io.domain.Card;
import curs3.io.domain.FlyingCard;
import curs3.io.domain.SwimmingCard;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CardRepository {
    private final Connection conn;

    public Card<?> save(Card<?> card) {
        try{

            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO cards(type) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            stmt.setString(1, card.getNumeCard());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                long generatedID = keys.getLong(1);
                card.setId(generatedID);
            }

            return card;

        } catch (SQLException e) {
            throw new RuntimeException("Cannot save card: " + e.getMessage());
        }
    }

    public void delete(Long id) {
        try{

            PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM cards WHERE cardID = ?"
            );
            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Cannot delete card: " + e.getMessage());
        }
    }

    public List<Card<?>> findAll() {
        List<Card<?>> list = new ArrayList<>();

        try{

            var stmt = conn.prepareStatement("SELECT * FROM cards");
            var rs = stmt.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("cardID");
                String type = rs.getString("type");

                Card<?> card;
                if (type.equalsIgnoreCase("FLYING"))
                    card = new FlyingCard(id, "FLYING");
                else
                    card = new SwimmingCard(id, "SWIMMING");

                list.add(card);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Cannot load cards: " + e.getMessage());
        }

        return list;
    }

    public Card<?> findById(Long id) {
        try{
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM cards WHERE cardID = ?"
            );
            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                long cid = rs.getLong("cardID");
                String type = rs.getString("type");

                if (type.equalsIgnoreCase("FLYING"))
                    return new FlyingCard(cid, "FLYING");
                else
                    return new SwimmingCard(cid, "SWIMMING");
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Cannot find card: " + e.getMessage());
        }
    }

}
