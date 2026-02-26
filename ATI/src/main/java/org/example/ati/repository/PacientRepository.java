package org.example.ati.repository;

import org.example.ati.domain.Pacient;
import org.example.ati.domain.Status;
import org.example.ati.domain.validator.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PacientRepository extends AbstractDbRepository<Pacient>{

    public PacientRepository(String url, String username, String password, Validator<Pacient> validator){
        super(url, username, password, validator);
    }

    @Override
    public Pacient createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_pacient");
        Integer cnp =  rs.getInt("cnp");
        Integer varsta = rs.getInt("varsta");
        Status prematur = Status.valueOf(rs.getString("prematur"));
        String diagnostic_principal = rs.getString("diagnostig_principal");
        Integer gravitate = rs.getInt("gravitate");
        Pacient pacient = new Pacient(cnp, varsta, prematur, diagnostic_principal, gravitate);
        pacient.setId(id);
        return pacient;
    }

    @Override
    public PreparedStatement findOneStatement(Connection connection, Integer id) throws SQLException {
        String query = "SELECT * FROM pacienti WHERE id_pacient = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement findAllStatement(Connection connection) throws SQLException {
        String query = "SELECT * FROM pacienti";
        return connection.prepareStatement(query);
    }

    @Override
    public PreparedStatement saveStatement(Connection connection, Pacient pacient) throws SQLException {
        String query = "INSERT INTO pacienti (cnp, varsta, prematur, diagnostig_principal, gravitate)VALUES (?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setInt(1, pacient.getCnp());
        statement.setInt(2, pacient.getVarsta());
        statement.setString(3, pacient.getPrematur().name());
        statement.setString(4, pacient.getDiagnostic_principal());
        statement.setInt(5, pacient.getGravitate());
        return statement;
    }

    @Override
    public PreparedStatement deleteStatement(Connection connection, Integer id) throws SQLException {
        String query = "DELETE FROM pacienti WHERE id_pacient = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement updateStatement(Connection connection, Pacient pacient) throws SQLException {
        String query = "UPDATE pacienti SET cnp=?, varsta=?, prematur=?, diagnostig_principal=?, gravitate=? WHERE id_pacient = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, pacient.getCnp());
        statement.setInt(2, pacient.getVarsta());
        statement.setString(3, pacient.getPrematur().name());
        statement.setString(4, pacient.getDiagnostic_principal());
        statement.setInt(5, pacient.getGravitate());
        statement.setInt(6, pacient.getId());
        return statement;
    }



}
