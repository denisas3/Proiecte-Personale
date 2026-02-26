package org.example.ati.repository;

import org.example.ati.domain.Pat;
import org.example.ati.domain.Status;
import org.example.ati.domain.validator.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatRepository extends AbstractDbRepository<Pat>{

    public PatRepository(String url, String username, String password, Validator<Pat> validator) {
        super(url, username, password, validator);
    }

    @Override
    public Pat createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_pat");
        String tip = rs.getString("tip");
        Status ventilatie = Status.valueOf(rs.getString("ventilatie"));
        Integer id_pacient = rs.getObject("id_pacient", Integer.class);
        Integer nr_pat_disponibile = rs.getInt("nr_pat_disponibile");
        Pat pat = new Pat(tip, ventilatie, id_pacient, nr_pat_disponibile);
        pat.setId(id);
        return pat;
    }

    @Override
    public PreparedStatement findOneStatement(Connection connection, Integer id) throws SQLException {
        String query = "SELECT * FROM paturi WHERE id_pat = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement findAllStatement(Connection connection) throws SQLException {
        String query = "SELECT * FROM paturi";
        return connection.prepareStatement(query);
    }

    @Override
    public PreparedStatement saveStatement(Connection connection, Pat pat) throws SQLException {
        String query = "INSERT INTO paturi (tip, ventilatie, id_pacient, nr_pat_disponibile) VALUES ( ?, ?, ?, ? )";
        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setString(1, pat.getTip());
        statement.setString(2, pat.getVentilatie().name());
        statement.setInt(3, pat.getId_pacient());
        statement.setInt(4, pat.getNr_pat_disponibile());
        return statement;
    }

    @Override
    public PreparedStatement deleteStatement(Connection connection, Integer id) throws SQLException {
        String query = "DELETE FROM paturi WHERE id_pat = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement updateStatement(Connection connection, Pat pat) throws SQLException {
        String query = "UPDATE paturi SET tip=?, ventilatie=?, id_pacient=?, nr_pat_disponibile=? WHERE id_pat = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, pat.getTip());
        statement.setString(2, pat.getVentilatie().name());
        statement.setInt(3, pat.getId_pacient());
        statement.setInt(4, pat.getNr_pat_disponibile());
        statement.setInt(5, pat.getId());
        return statement;
    }
}
