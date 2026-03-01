package org.example.restaurante.repository;

import org.example.restaurante.domain.Oras;
import org.example.restaurante.domain.validator.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrasRepository extends AbstractDbRepository<Oras>{

    public OrasRepository(String url, String username, String password, Validator<Oras> validator){
        super(url, username, password, validator);
    }

    @Override
    public Oras createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_oras");
        String nume = rs.getString("nume");
        Oras oras = new Oras(nume);
        oras.setId(id);
        return oras;
    }

    @Override
    public PreparedStatement findOneStatement(Connection connection, Integer id) throws SQLException {
        String query = "SELECT * FROM orase WHERE id_oras = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement findAllStatement(Connection connection) throws SQLException {
        String query = "SELECT * FROM orase";
        return connection.prepareStatement(query);
    }

    @Override
    public PreparedStatement saveStatement(Connection connection, Oras oras) throws SQLException {
        String query = "INSERT INTO orase (nume)VALUES ( ?)";
        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setString(1, oras.getNume());
        return statement;
    }

    @Override
    public PreparedStatement deleteStatement(Connection connection, Integer id) throws SQLException {
        String query = "DELETE FROM orase WHERE id_oras = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement updateStatement(Connection connection, Oras oras) throws SQLException {
        String query = "UPDATE orase SET nume = ? WHERE id_oras = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, oras.getNume());
        statement.setInt(2, oras.getId());
        return statement;
    }



}
