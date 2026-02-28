package org.example.restaurante.repository;

import org.example.restaurante.domain.Medic;
import org.example.restaurante.domain.Status;
import org.example.restaurante.domain.validator.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicRepository extends AbstractDbRepository<Medic>{

    public MedicRepository(String url, String username, String password, Validator<Medic> validator) {
        super(url, username, password, validator);
    }

    @Override
    public Medic createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_medic");
        Integer id_sectie = rs.getInt("id_sectie");
        String nume = rs.getString("nume");
        Integer vechime = rs.getInt("vechime");
        Status rezident = Status.valueOf(rs.getString("rezident"));
        Medic medic = new Medic(id_sectie, nume, vechime, rezident);
        medic.setId(id);
        return medic;
    }

    @Override
    public PreparedStatement findOneStatement(Connection connection, Integer id) throws SQLException {
        String query = "SELECT * FROM medici WHERE id_medic = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement findAllStatement(Connection connection) throws SQLException {
        String query = "SELECT * FROM medici";
        return connection.prepareStatement(query);
    }

    @Override
    public PreparedStatement saveStatement(Connection connection, Medic medic) throws SQLException {
        String query = "INSERT INTO medici (id_sectie, nume, vechime, rezident) VALUES ( ?, ?, ?, ? )";
        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setInt(1, medic.getId_sectie());
        statement.setString(2, medic.getNume());
        statement.setInt(3, medic.getVechime());
        statement.setString(4, medic.getRezident().name());
        return statement;
    }

    @Override
    public PreparedStatement deleteStatement(Connection connection, Integer id) throws SQLException {
        String query = "DELETE FROM medici WHERE id_medic = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement updateStatement(Connection connection, Medic medic) throws SQLException {
        String query = "UPDATE medici SET id_sectie=?, nume=?, vechime=?, rezident=? WHERE id_medic = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, medic.getId_sectie());
        statement.setString(2, medic.getNume());
        statement.setInt(3, medic.getVechime());
        statement.setString(4, medic.getRezident().name());
        statement.setInt(5, medic.getId());
        return statement;
    }
}
