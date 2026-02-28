package org.example.restaurante.repository;

import org.example.restaurante.domain.Detaliu;
import org.example.restaurante.domain.validator.Validator;

import java.sql.*;

public class DetaliuRepository extends AbstractDbRepository<Detaliu>{

    public DetaliuRepository(String url, String username, String password, Validator<Detaliu> validator){
        super(url, username, password, validator);
    }

    @Override
    public Detaliu createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_detaliu");
        Integer id_masina = rs.getInt("id_masina");
        String detalii = rs.getString("delatii");
        String comentariu = rs.getString("comentariu");
        Detaliu detaliu = new Detaliu(id_masina, detalii,comentariu);
        detaliu.setId(id);
        return detaliu;
    }

    @Override
    public PreparedStatement findOneStatement(Connection connection, Integer id) throws SQLException {
        String query = "SELECT * FROM detalii WHERE id_detaliu = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement findAllStatement(Connection connection) throws SQLException {
        String query = "SELECT * FROM detalii";
        return connection.prepareStatement(query);
    }

    @Override
    public PreparedStatement saveStatement(Connection connection, Detaliu detaliu) throws SQLException {
        String query = "INSERT INTO detalii (id_masina, delatii, comentariu)VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setInt(1, detaliu.getId_masina());
        statement.setString(2, detaliu.getDetaliu());
        statement.setString(3, detaliu.getComentariu());
        return statement;
    }

    @Override
    public PreparedStatement deleteStatement(Connection connection, Integer id) throws SQLException {
        String query = "DELETE FROM detalii WHERE id_detaliu = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement updateStatement(Connection connection, Detaliu detaliu) throws SQLException {
        String query = "UPDATE detalii SET id_masina=?, delatii=?, comentariu=? WHERE id_detaliu = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, detaliu.getId_masina());
        statement.setString(2, detaliu.getDetaliu());
        statement.setString(3, detaliu.getComentariu());
        statement.setInt(4, detaliu.getId());
        return statement;
    }


}
