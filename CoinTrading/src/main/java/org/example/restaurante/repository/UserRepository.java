package org.example.restaurante.repository;

import org.example.restaurante.domain.User;
import org.example.restaurante.domain.validator.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository extends AbstractDbRepository<User>{

    public UserRepository(String url, String username, String password, Validator<User> validator){
        super(url, username, password, validator);
    }

    @Override
    public User createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_user");
        String username = rs.getString("username");
        Float buget = rs.getFloat("buget");
        User user = new User(username, buget);
        user.setId(id);
        return user;
    }

    @Override
    public PreparedStatement findOneStatement(Connection connection, Integer id) throws SQLException {
        String query = "SELECT * FROM useri WHERE id_user = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement findAllStatement(Connection connection) throws SQLException {
        String query = "SELECT * FROM useri";
        return connection.prepareStatement(query);
    }

    @Override
    public PreparedStatement saveStatement(Connection connection, User user) throws SQLException {
        String query = "INSERT INTO useri (username,buget)VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setString(1, user.getUsername());
        statement.setFloat(2, user.getBuget());
        return statement;
    }

    @Override
    public PreparedStatement deleteStatement(Connection connection, Integer id) throws SQLException {
        String query = "DELETE FROM useri WHERE id_user = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement updateStatement(Connection connection, User user) throws SQLException {
        String query = "UPDATE useri SET username = ?, buget = ? WHERE id_user = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getUsername());
        statement.setFloat(2, user.getBuget());
        statement.setInt(3, user.getId());
        return statement;
    }



}
