package org.example.restaurante.repository;

import org.example.restaurante.domain.MenuItem;
import org.example.restaurante.domain.validator.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuItemRepository extends AbstractDbRepository<MenuItem>{

    public MenuItemRepository(String url, String username, String password, Validator<MenuItem> validator) {
        super(url, username, password, validator);
    }

    @Override
    public MenuItem createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String category = rs.getString("category");
        String item = rs.getString("item");
        Float price = rs.getFloat("price");
        String currency = rs.getString("currency");
        MenuItem menu = new MenuItem(category, item, price, currency);
        menu.setId(id);
        return menu;
    }

    @Override
    public PreparedStatement findOneStatement(Connection connection, Integer id) throws SQLException {
        String query = "SELECT * FROM menuitem WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement findAllStatement(Connection connection) throws SQLException {
        String query = "SELECT * FROM menuitem";
        return connection.prepareStatement(query);
    }

    @Override
    public PreparedStatement saveStatement(Connection connection, MenuItem menu) throws SQLException {
        String query = "INSERT INTO menuitem (category, item, price, currency) VALUES ( ?, ?, ?, ? )";
        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setString(1, menu.getCategory());
        statement.setString(2, menu.getItem());
        statement.setFloat(3, menu.getPrice());
        statement.setString(4, menu.getCurrency());
        return statement;
    }

    @Override
    public PreparedStatement deleteStatement(Connection connection, Integer id) throws SQLException {
        String query = "DELETE FROM menuitem WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement updateStatement(Connection connection, MenuItem menu) throws SQLException {
        String query = "UPDATE menuitem SET category = ?, item = ?, price = ?, currency = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, menu.getCategory());
        statement.setString(2, menu.getItem());
        statement.setFloat(3, menu.getPrice());
        statement.setString(4, menu.getCurrency());
        statement.setInt(5, menu.getId());
        return statement;
    }
}
