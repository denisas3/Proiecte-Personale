package org.example.restaurante.repository;

import org.example.restaurante.domain.Status;
import org.example.restaurante.domain.Transaction;
import org.example.restaurante.domain.validator.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class TransactionRepository extends AbstractDbRepository<Transaction>{

    public TransactionRepository(String url, String username, String password, Validator<Transaction> validator) {
        super(url, username, password, validator);
    }

    @Override
    public Transaction createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_transaction");
        Integer coinId = rs.getInt("id_coin");
        Integer userId = rs.getInt("id_user");
        Status status = Status.valueOf(rs.getString("type"));
        Double price = rs.getDouble("price");
        LocalDateTime date = rs.getTimestamp("timestamp").toLocalDateTime();
        Transaction transaction = new Transaction(userId,coinId,status,price,date);
        transaction.setId(id);
        return transaction;
    }

    @Override
    public PreparedStatement findOneStatement(Connection connection, Integer id) throws SQLException {
        String query = "SELECT * FROM transactions WHERE id_transaction = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement saveStatement(Connection connection, Transaction transaction) throws SQLException {
        String query = "INSERT INTO transactions (id_user, id_coin, type, price, timestamp) VALUES ( ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setInt(1, transaction.getUserId());
        statement.setInt(2, transaction.getCoinId());
        statement.setString(3, transaction.getType().name());
        statement.setDouble(4,transaction.getPrice());
        statement.setTimestamp(5, java.sql.Timestamp.valueOf(transaction.getTimestamp()));
        return statement;
    }

    @Override
    public PreparedStatement findAllStatement(Connection connection) throws SQLException {
        String query = "SELECT * FROM transactions";
        return connection.prepareStatement(query);
    }

    @Override
    public PreparedStatement deleteStatement(Connection connection, Integer id) throws SQLException {
        String query = "DELETE FROM transactions WHERE id_transaction = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement updateStatement(Connection connection, Transaction transaction) throws SQLException {
        String query = "UPDATE transactions SET id_user=?, id_coin=?, type=?, price=?, timestamp=? WHERE id_transaction= ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, transaction.getUserId());
        statement.setInt(2, transaction.getCoinId());
        statement.setString(3, transaction.getType().name());
        statement.setDouble(4,transaction.getPrice());
        statement.setTimestamp(5, java.sql.Timestamp.valueOf(transaction.getTimestamp()));
        statement.setInt(6, transaction.getId());
        return statement;
    }

}
