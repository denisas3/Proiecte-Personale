package org.example.restaurante.repository;

import org.example.restaurante.domain.Sectie;
import org.example.restaurante.domain.validator.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SectieRepository extends AbstractDbRepository<Sectie>{

    public SectieRepository(String url, String username, String password, Validator<Sectie> validator){
        super(url, username, password, validator);
    }

    @Override
    public Sectie createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_sectie");
        String nume = rs.getString("nume");
        Integer pret = rs.getInt("pretconsultatie");
        Integer durata = rs.getInt("duratamaxconsultatie");
        Sectie sectie = new Sectie(nume, pret, durata);
        sectie.setId(id);
        return sectie;
    }

    @Override
    public PreparedStatement findOneStatement(Connection connection, Integer id) throws SQLException {
        String query = "SELECT * FROM sectie WHERE id_sectie = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement findAllStatement(Connection connection) throws SQLException {
        String query = "SELECT * FROM sectie";
        return connection.prepareStatement(query);
    }

    @Override
    public PreparedStatement saveStatement(Connection connection, Sectie sectie) throws SQLException {
        String query = "INSERT INTO sectie (name, pretconsultatie, duratamaxconsultatie)VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setString(1, sectie.getNume());
        statement.setInt(2, sectie.getPret());
        statement.setInt(3, sectie.getDurata());
        return statement;
    }

    @Override
    public PreparedStatement deleteStatement(Connection connection, Integer id) throws SQLException {
        String query = "DELETE FROM sectie WHERE id_sectie = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement updateStatement(Connection connection, Sectie sectie) throws SQLException {
        String query = "UPDATE sectie SET name=?, pretconsultatie=?, duratamaxconsultatie = ? WHERE id_sectie = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, sectie.getNume());
        statement.setInt(2, sectie.getPret());
        statement.setInt(3, sectie.getDurata());
        statement.setInt(4, sectie.getId());
        return statement;
    }



}
