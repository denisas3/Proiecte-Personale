package org.example.restaurante.repository;

import org.example.restaurante.domain.Masina;
import org.example.restaurante.domain.Status;
import org.example.restaurante.domain.validator.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MasinaRepository extends AbstractDbRepository<Masina>{

    public MasinaRepository(String url, String username, String password, Validator<Masina> validator) {
        super(url, username, password, validator);
    }

    @Override
    public Masina createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_masina");
        String denumire = rs.getString("denumire_masina");
        String descriere = rs.getString("descriere_masina");
        Double price = rs.getDouble("pret_de_baza");
        String statusStr = rs.getString("status");
        Status status = Status.valueOf(statusStr.trim().toUpperCase());
        Masina masina = new Masina(denumire,descriere,price,status);
        masina.setId(id);
        return masina;
    }

    @Override
    public PreparedStatement findOneStatement(Connection connection, Integer id) throws SQLException {
        String query = "SELECT * FROM masini WHERE id_masina = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement findAllStatement(Connection connection) throws SQLException {
        String query = "SELECT * FROM masini";
        return connection.prepareStatement(query);
    }

    @Override
    public PreparedStatement saveStatement(Connection connection, Masina masina) throws SQLException {
        String query = "INSERT INTO masini (denumire_masina, descriere_masin, pret_de_baza, status) VALUES ( ?, ?, ?, ? )";
        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setString(1, masina.getDenumire_masina());
        statement.setString(2, masina.getDescriere_masina());
        statement.setDouble(3, masina.getPret_de_baza());
        statement.setString(4, masina.getStatur_curent().toString());
        return statement;
    }

    @Override
    public PreparedStatement deleteStatement(Connection connection, Integer id) throws SQLException {
        String query = "DELETE FROM masini WHERE id_masina = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement updateStatement(Connection connection, Masina masina) throws SQLException {
        String query = "UPDATE masini SET denumire_masina = ?, descriere_masina = ?, pret_de_baza =? , status = ? WHERE id_masina = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, masina.getDenumire_masina());
        statement.setString(2, masina.getDescriere_masina());
        statement.setDouble(3, masina.getPret_de_baza());
        statement.setString(4, masina.getStatur_curent().toString());
        statement.setInt(5, masina.getId());
        return statement;
    }
}
