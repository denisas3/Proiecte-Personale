package org.example.restaurante.repository;

import org.example.restaurante.domain.Coins;
import org.example.restaurante.domain.validator.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CoinRepository extends AbstractDbRepository<Coins>{

    public CoinRepository(String url, String username, String password, Validator<Coins> validator) {
        super(url, username, password, validator);
    }

    @Override
    public Coins createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_coin");
        String nume = rs.getString("nume");
        Float pret = rs.getFloat("pret");
        Coins coin = new Coins(nume,pret);
        coin.setId(id);
        return coin;
    }

    @Override
    public PreparedStatement findOneStatement(Connection connection, Integer id) throws SQLException {
        String query = "SELECT * FROM coins WHERE id_coin = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement findAllStatement(Connection connection) throws SQLException {
        String query = "SELECT * FROM coins";
        return connection.prepareStatement(query);
    }

    @Override
    public PreparedStatement saveStatement(Connection connection, Coins coin) throws SQLException {
        String query = "INSERT INTO coins (nume, pret) VALUES ( ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setString(1, coin.getNume());
        statement.setFloat(2, coin.getPret());
        return statement;
    }

    @Override
    public PreparedStatement deleteStatement(Connection connection, Integer id) throws SQLException {
        String query = "DELETE FROM coins WHERE id_coin = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement updateStatement(Connection connection, Coins coin) throws SQLException {
        String query = "UPDATE coins SET nume =?, pret=? WHERE id_coin = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, coin.getNume());
        statement.setFloat(2, coin.getPret());
        statement.setInt(3, coin.getId());
        return statement;
    }
}
