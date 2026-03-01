package org.example.restaurante.repository;

import org.example.restaurante.domain.Tren;
import org.example.restaurante.domain.validator.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrenRepository extends AbstractDbRepository<Tren>{

    public TrenRepository(String url, String username, String password, Validator<Tren> validator) {
        super(url, username, password, validator);
    }

    @Override
    public Tren createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_tren");
        String nume = rs.getString("nume");
        Tren tren = new Tren(nume);
        tren.setId(id);
        return tren;
    }

    @Override
    public PreparedStatement findOneStatement(Connection connection, Integer id) throws SQLException {
        String query = "SELECT * FROM trenuri WHERE id_tren = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement saveStatement(Connection connection, Tren tren) throws SQLException {
        String query = "INSERT INTO trenuri nume) VALUES ( ?)";
        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setString(1,tren.getNume());
        return statement;
    }

    @Override
    public PreparedStatement findAllStatement(Connection connection) throws SQLException {
        String query = "SELECT * FROM trenuri";
        return connection.prepareStatement(query);
    }

    @Override
    public PreparedStatement deleteStatement(Connection connection, Integer id) throws SQLException {
        String query = "DELETE FROM trenuri WHERE id_tren = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement updateStatement(Connection connection, Tren tren) throws SQLException {
        String query = "UPDATE trenuri SET nume=? WHERE id_tren= ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1,tren.getNume());
        statement.setInt(2, tren.getId());
        return statement;
    }

}
