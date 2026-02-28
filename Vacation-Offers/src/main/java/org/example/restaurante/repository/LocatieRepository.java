package org.example.restaurante.repository;

import org.example.restaurante.domain.Locatie;
import org.example.restaurante.domain.validator.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LocatieRepository extends AbstractDbRepository<Locatie>{

    public LocatieRepository(String url, String username, String password, Validator<Locatie> validator){
        super(url, username, password, validator);
    }

    @Override
    public Locatie createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_locatie");
        String nume = rs.getString("nume");
        Locatie locatie = new Locatie(nume);
        locatie.setId(id);
        return locatie;
    }

    @Override
    public PreparedStatement findOneStatement(Connection connection, Integer id) throws SQLException {
        String query = "SELECT * FROM locatii WHERE id_locatie = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement findAllStatement(Connection connection) throws SQLException {
        String query = "SELECT * FROM locatii";
        return connection.prepareStatement(query);
    }

    @Override
    public PreparedStatement saveStatement(Connection connection, Locatie locatie) throws SQLException {
        String query = "INSERT INTO locatii (nume)VALUES (?)";
        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setString(1, locatie.getNume());
        return statement;
    }

    @Override
    public PreparedStatement deleteStatement(Connection connection, Integer id) throws SQLException {
        String query = "DELETE FROM locatii WHERE id_locatie = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement updateStatement(Connection connection, Locatie locatie) throws SQLException {
        String query = "UPDATE locatii SET nume = ? WHERE id_locatie = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, locatie.getNume());
        statement.setInt(2, locatie.getId());
        return statement;
    }



}
