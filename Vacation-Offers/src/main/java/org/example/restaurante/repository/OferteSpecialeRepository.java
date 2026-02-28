package org.example.restaurante.repository;

import org.example.restaurante.domain.OferteSpeciale;
import org.example.restaurante.domain.validator.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class OferteSpecialeRepository extends AbstractDbRepository<OferteSpeciale>{

    public OferteSpecialeRepository(String url, String username, String password, Validator<OferteSpeciale> validator) {
        super(url, username, password, validator);
    }

    @Override
    public OferteSpeciale createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_oferta");
        Integer id_hotel = rs.getInt("id_hotel");
        LocalDate data_inceput = rs.getTimestamp("data_inceput").toLocalDateTime().toLocalDate();
        LocalDate data_final = rs.getTimestamp("data_final").toLocalDateTime().toLocalDate();
        Integer procent = rs.getInt("procent");
        OferteSpeciale oferta = new OferteSpeciale(id_hotel, data_inceput, data_final, procent);
        oferta.setId(id);
        return oferta;
    }

    @Override
    public PreparedStatement findOneStatement(Connection connection, Integer id) throws SQLException {
        String query = "SELECT * FROM ofertespeciale WHERE id_oferta = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement saveStatement(Connection connection, OferteSpeciale oferta) throws SQLException {
        String query = "INSERT INTO ofertespeciale (id_hotel,data_inceput,data_final,procent) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setInt(1,oferta.getId_hotel());
        statement.setObject(2, oferta.getData_inceput());
        statement.setObject(3, oferta.getData_final());
        statement.setInt(4,oferta.getProcent());
        return statement;
    }

    @Override
    public PreparedStatement findAllStatement(Connection connection) throws SQLException {
        String query = "SELECT * FROM ofertespeciale";
        return connection.prepareStatement(query);
    }

    @Override
    public PreparedStatement deleteStatement(Connection connection, Integer id) throws SQLException {
        String query = "DELETE FROM ofertespeciale WHERE id_oferta = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement updateStatement(Connection connection, OferteSpeciale oferta) throws SQLException {
        String query = "UPDATE ofertespeciale SET id_hotel=?,data_inceput=?,data_final=?,procente=? WHERE id_oferta= ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1,oferta.getId_hotel());
        statement.setObject(2, oferta.getData_inceput());
        statement.setObject(3, oferta.getData_final());
        statement.setInt(4,oferta.getProcent());
        statement.setInt(5, oferta.getId());
        return statement;
    }

}
