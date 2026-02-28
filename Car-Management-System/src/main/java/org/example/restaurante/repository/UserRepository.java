package org.example.restaurante.repository;

import org.example.restaurante.domain.User;
import org.example.restaurante.domain.validator.Validator;

import java.sql.*;
import java.util.Optional;

public class UserRepository extends AbstractDbRepository<User>{

    public UserRepository(String url, String username, String password, Validator<User> validator){
        super(url, username, password, validator);
    }

    @Override
    public User createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_use");
        String username = rs.getString("username");
        String password = rs.getString("password");
        User user = new User(username, password);
        user.setId(id);
        return user;
    }

    @Override
    public PreparedStatement findOneStatement(Connection connection, Integer id) throws SQLException {
        String query = "SELECT * FROM users WHERE id_use = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement findAllStatement(Connection connection) throws SQLException {
        String query = "SELECT * FROM users";
        return connection.prepareStatement(query);
    }

    @Override
    public PreparedStatement saveStatement(Connection connection, User user) throws SQLException {
        String query = "INSERT INTO users (username,password)VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        return statement;
    }

    @Override
    public PreparedStatement deleteStatement(Connection connection, Integer id) throws SQLException {
        String query = "DELETE FROM users WHERE id_use = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement updateStatement(Connection connection, User user) throws SQLException {
        String query = "UPDATE users SET username = ?, password = ? WHERE id_use = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.setInt(3, user.getId());
        return statement;
    }

    /**
     * Găsește un utilizator după username și password
     * @param username username-ul utilizatorului
     * @param password parola utilizatorului
     * @return Optional cu User dacă există, Optional.empty() altfel
     */
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(url, this.username, this.password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User user = createEntity(resultSet);
                return Optional.of(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    /*2. Metodă doar pentru IDDacă vrei doar ID-ul, nu tot obiectul:java/**
     * Găsește ID-ul utilizatorului după username și password
     * @param username username-ul utilizatorului
     * @param password parola utilizatorului
     * @return Optional cu ID dacă există, Optional.empty() altfel
     */
    public Optional<Integer> findIdByUsernameAndPassword(String username, String password) {
        String query = "SELECT id_use FROM users WHERE username = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(url, this.username, this.password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id_use");
                return Optional.of(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();}

        return Optional.empty();
    }

}
