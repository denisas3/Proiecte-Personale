package org.example.restaurante.repository;

import org.example.restaurante.domain.Statie;
import org.example.restaurante.domain.validator.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatieRepository extends AbstractDbRepository<Statie>{

    public StatieRepository(String url, String username, String password, Validator<Statie> validator) {
        super(url, username, password, validator);
    }

    @Override
    public Statie createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_statie");
        Integer id_tren = rs.getInt("id_tren");
        Integer id_oras_plecare = rs.getInt("id_oras_plecare");
        Integer id_oras_sosire = rs.getInt("id_oras_sosire");
        Statie statie = new Statie(id_tren,id_oras_plecare, id_oras_sosire);
        statie.setId(id);
        return statie;
    }

    @Override
    public PreparedStatement findOneStatement(Connection connection, Integer id) throws SQLException {
        String query = "SELECT * FROM statii WHERE id_statie = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement findAllStatement(Connection connection) throws SQLException {
        String query = "SELECT * FROM statii";
        return connection.prepareStatement(query);
    }

    @Override
    public PreparedStatement saveStatement(Connection connection, Statie statie) throws SQLException {
        String query = "INSERT INTO statii (id_tren, id_oras_plecare, id_oras_sosire) VALUES (?, ?, ? )";
        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setInt(1, statie.getId_tren());
        statement.setInt(2, statie.getId_oras_plecare());
        statement.setInt(3, statie.getId_oras_sosier());
        return statement;
    }

    @Override
    public PreparedStatement deleteStatement(Connection connection, Integer id) throws SQLException {
        String query = "DELETE FROM statii WHERE id_statie = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement updateStatement(Connection connection, Statie statie) throws SQLException {
        String query = "UPDATE statii SET id_tren=?, id_oras_plecare=?, id_oras_sosire=? WHERE id_statie = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, statie.getId_tren());
        statement.setInt(2, statie.getId_oras_plecare());
        statement.setInt(3, statie.getId_oras_sosier());
        statement.setInt(4, statie.getId());
        return statement;
    }
}
