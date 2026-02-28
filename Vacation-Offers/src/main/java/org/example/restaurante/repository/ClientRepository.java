package org.example.restaurante.repository;

import org.example.restaurante.domain.Client;
import org.example.restaurante.domain.Pasiuni;
import org.example.restaurante.domain.validator.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientRepository extends AbstractDbRepository<Client>{

    public ClientRepository(String url, String username, String password, Validator<Client> validator) {
        super(url, username, password, validator);
    }

    @Override
    public Client createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_client");
        String nume = rs.getString("nume");
        Integer grad_fidelitete  = rs.getInt("grad_fidelitete");
        Integer varsta = rs.getInt("varsta");
        Pasiuni pasiune = Pasiuni.valueOf(rs.getString("pasiune"));
        Client client = new Client(nume, grad_fidelitete, varsta, pasiune);
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
        String query = "INSERT INTO clienti (nume,grad_fidelitete,varsta,pasiune) VALUES (?, ?, ?, ? )";
        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setString(1, client.getNume());
        statement.setInt(2, client.getGrad_fidelitate());
        statement.setInt(3, client.getVarsta());
        statement.setString(4, client.getPasiune().name());
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
        String query = "UPDATE clienti SET nume=?,grad_fidelitete=?,varsta=?,pasiune=? WHERE id_client = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, client.getNume());
        statement.setInt(2, client.getGrad_fidelitate());
        statement.setInt(3, client.getVarsta());
        statement.setString(4, client.getPasiune().name());
        statement.setInt(5, client.getId());
        return statement;
    }
}
