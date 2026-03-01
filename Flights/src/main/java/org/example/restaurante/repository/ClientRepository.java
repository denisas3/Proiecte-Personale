package org.example.restaurante.repository;

import org.example.restaurante.domain.Client;
import org.example.restaurante.domain.validator.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientRepository extends AbstractDbRepository<Client>{

    public ClientRepository(String url, String username, String password, Validator<Client> validator){
        super(url, username, password, validator);
    }

    @Override
    public Client createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_client");
        String username = rs.getString("username");
        String nume = rs.getString("nume");
        Client client = new Client(username, nume);
        client.setId(id);
        return client;
    }

    @Override
    public PreparedStatement findOneStatement(Connection connection, Integer id) throws SQLException {
        String query = "SELECT * FROM clienti WHERE id_client = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement findAllStatement(Connection connection) throws SQLException {
        String query = "SELECT * FROM clienti";
        return connection.prepareStatement(query);
    }

    @Override
    public PreparedStatement saveStatement(Connection connection, Client client) throws SQLException {
        String query = "INSERT INTO clienti (username,nume)VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setString(1, client.getUsername());
        statement.setString(2, client.getNume());
        return statement;
    }

    @Override
    public PreparedStatement deleteStatement(Connection connection, Integer id) throws SQLException {
        String query = "DELETE FROM clienti WHERE id_client = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement updateStatement(Connection connection, Client client) throws SQLException {
        String query = "UPDATE clienti SET username = ?, nume = ? WHERE id_client = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, client.getUsername());
        statement.setString(2, client.getNume());
        statement.setInt(3, client.getId());
        return statement;
    }



}
