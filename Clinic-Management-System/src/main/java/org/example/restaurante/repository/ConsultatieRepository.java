package org.example.restaurante.repository;

import org.example.restaurante.domain.Consultatie;
import org.example.restaurante.domain.validator.Validator;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class ConsultatieRepository extends AbstractDbRepository<Consultatie>{

    public ConsultatieRepository(String url, String username, String password, Validator<Consultatie> validator) {
        super(url, username, password, validator);
    }

    @Override
    public Consultatie createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_consultatie");
        Integer id_medic = rs.getInt("id_medic");
        Integer cnp = rs.getInt("cnp");
        String nume = rs.getString("nume");

        LocalDate date = rs.getDate("data").toLocalDate();

        LocalTime ora = rs.getTime("ora").toLocalTime();

        Consultatie consultatie = new Consultatie(id_medic, cnp, nume, date, ora);
        consultatie.setId(id);
        return consultatie;
    }

    @Override
    public PreparedStatement findOneStatement(Connection connection, Integer id) throws SQLException {
        String query = "SELECT * FROM consultatii WHERE id_consultatie = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement saveStatement(Connection connection, Consultatie consultatie) throws SQLException {
        String query = "INSERT INTO consultatii (id_medic,cnp,nume,data,ora) VALUES (?,?,?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setInt(1,consultatie.getId_medic());
        statement.setInt(2,consultatie.getCnp());
        statement.setString(3, consultatie.getNume());

        statement.setDate(4, java.sql.Date.valueOf(consultatie.getData()));

        statement.setTime(5, java.sql.Time.valueOf(consultatie.getOra()) );

        return statement;
    }

    @Override
    public PreparedStatement findAllStatement(Connection connection) throws SQLException {
        String query = "SELECT * FROM consultatii";
        return connection.prepareStatement(query);
    }

    @Override
    public PreparedStatement deleteStatement(Connection connection, Integer id) throws SQLException {
        String query = "DELETE FROM consultatii WHERE id_consultatie = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement updateStatement(Connection connection, Consultatie consultatie) throws SQLException {
        String query = "UPDATE consultatii SET id_medic=?,cnp=?,nume=?,data=?,ora=? WHERE id_consultatie= ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1,consultatie.getId_medic());
        statement.setInt(2,consultatie.getCnp());
        statement.setString(3, consultatie.getNume());

        statement.setDate(4, java.sql.Date.valueOf(consultatie.getData()));

        statement.setTime(5, java.sql.Time.valueOf(consultatie.getOra()) );

        statement.setInt(6, consultatie.getId());
        return statement;
    }

}
