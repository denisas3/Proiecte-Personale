package org.example.restaurante.repository;

import org.example.restaurante.domain.Tabel;
import org.example.restaurante.domain.validator.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableRepository extends AbstractDbRepository<Tabel>{

    public TableRepository(String url, String username, String password, Validator<Tabel> validator){
        super(url, username, password, validator);
    }

    @Override
    public Tabel createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_table");
        Tabel tabel = new Tabel();
        tabel.setId(id);
        return tabel;
    }

    @Override
    public PreparedStatement findOneStatement(Connection connection, Integer id) throws SQLException {
        String query = "SELECT * FROM tables WHERE id_table = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement findAllStatement(Connection connection) throws SQLException {
        String query = "SELECT * FROM tables";
        return connection.prepareStatement(query);
    }

    @Override
    public PreparedStatement saveStatement(Connection connection, Tabel tabel) throws SQLException {
        String query = "INSERT INTO tables id VALUES (?)";
        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setString(1, tabel.getId().toString());
        return statement;
    }

    @Override
    public PreparedStatement deleteStatement(Connection connection, Integer id) throws SQLException {
        String query = "DELETE FROM tables WHERE id_table = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement updateStatement(Connection connection, Tabel tabel) throws SQLException {
        String query = "UPDATE tables SET id = ? WHERE id_table = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, tabel.getId());
        return statement;
    }



}
