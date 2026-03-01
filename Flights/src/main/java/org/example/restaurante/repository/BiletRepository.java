package org.example.restaurante.repository;

import org.example.restaurante.domain.Bilet;
import org.example.restaurante.domain.validator.Validator;

import java.sql.*;
import java.time.LocalDateTime;

public class BiletRepository extends AbstractDbRepository<Bilet>{

    public BiletRepository(String url, String username, String password, Validator<Bilet> validator) {
        super(url, username, password, validator);
    }

    @Override
    public Bilet createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_bilet");
        String username = rs.getString("username");
        Integer id_zbor = rs.getInt("id_zbor");
        LocalDateTime data_cumparare = rs.getTimestamp("data_cumparare").toLocalDateTime();
        Bilet bilet = new Bilet(username,id_zbor,data_cumparare);
        bilet.setId(id);
        return bilet;
    }

    @Override
    public PreparedStatement findOneStatement(Connection connection, Integer id) throws SQLException {
        String query = "SELECT * FROM bilet WHERE id_bilet = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement saveStatement(Connection connection, Bilet bilet) throws SQLException {
        String query = "INSERT INTO bilet (username, id_zbor, data_cumparare) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setString(1,bilet.getUsername());
        statement.setInt(2, bilet.getId_zbor());
        statement.setObject(3,bilet.getData_cumparare());
        return statement;
    }

    @Override
    public PreparedStatement findAllStatement(Connection connection) throws SQLException {
        String query = "SELECT * FROM bilet";
        return connection.prepareStatement(query);
    }

    @Override
    public PreparedStatement deleteStatement(Connection connection, Integer id) throws SQLException {
        String query = "DELETE FROM bilet WHERE id_bilet = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement updateStatement(Connection connection, Bilet bilet) throws SQLException {
        String query = "UPDATE bilet SET username=?, id_zbor=?, data_cumparare=? WHERE id_bilet= ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1,bilet.getUsername());
        statement.setInt(2, bilet.getId_zbor());
        statement.setObject(3,bilet.getData_cumparare());
        statement.setInt(4, bilet.getId());
        return statement;
    }

    /// PENTRU A VEDEA CATE LOCURI MAI SUNT IN AVION
    public int countByZborId(int idZbor) {
        String sql = "SELECT COUNT(*) FROM bilet WHERE id_zbor = ?";
        try (Connection c = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idZbor);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
