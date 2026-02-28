package org.example.restaurante.repository;

import org.example.restaurante.domain.Rezervare;
import org.example.restaurante.domain.validator.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RezervareRepository extends AbstractDbRepository<Rezervare>{

    public RezervareRepository(String url, String username, String password, Validator<Rezervare> validator) {
        super(url, username, password, validator);
    }

    @Override
    public Rezervare createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_rezervare");
        Integer id_client  = rs.getInt("id_client");
        Integer id_hotel = rs.getInt("id_hotel");
        LocalDate data_inceput = rs.getDate("data_inceput").toLocalDate();
        LocalDate data_final = rs.getDate("data_final").toLocalDate();
        Rezervare rezervare = new Rezervare(id_client,id_hotel,data_inceput,data_final);
        rezervare.setId(id);
        return rezervare;
    }

    @Override
    public PreparedStatement findOneStatement(Connection connection, Integer id) throws SQLException {
        String query = "SELECT * FROM rezervari WHERE id_rezervare = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement findAllStatement(Connection connection) throws SQLException {
        String query = "SELECT * FROM rezervari";
        return connection.prepareStatement(query);
    }

    @Override
    public PreparedStatement saveStatement(Connection connection, Rezervare rezervare) throws SQLException {
        String query = "INSERT INTO rezervari (id_client,id_hotel,data_inceput,data_final) VALUES (?, ?, ?, ? )";
        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setInt(1, rezervare.getId_client());
        statement.setInt(2, rezervare.getId_hotel());
        statement.setObject(3,rezervare.getData_inceput());
        statement.setObject(4,rezervare.getData_final());
        return statement;
    }

    @Override
    public PreparedStatement deleteStatement(Connection connection, Integer id) throws SQLException {
        String query = "DELETE FROM rezervari WHERE id_rezervare = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement updateStatement(Connection connection, Rezervare rezervare) throws SQLException {
        String query = "UPDATE rezervari SET id_client=?,id_hotel=?,data_inceput=?,data_final=? WHERE id_rezervare = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, rezervare.getId_client());
        statement.setInt(2, rezervare.getId_hotel());
        statement.setObject(3,rezervare.getData_inceput());
        statement.setObject(4,rezervare.getData_final());
        statement.setInt(5, rezervare.getId());
        return statement;
    }
}
