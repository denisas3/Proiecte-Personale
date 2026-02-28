package org.example.restaurante.repository;

import org.example.restaurante.domain.OrderItems;
import org.example.restaurante.domain.validator.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemRepository extends AbstractDbRepository<OrderItems>{

    public OrderItemRepository(String url, String username, String password, Validator<OrderItems> validator){
        super(url, username, password, validator);
    }

    @Override
    public OrderItems createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int order_id = rs.getInt("order_id");
        Integer id_menuItem = rs.getInt("menuitem_id");
        OrderItems order = new OrderItems(order_id,id_menuItem);
        order.setId(id);
        return order;
    }

    @Override
    public PreparedStatement findOneStatement(Connection connection, Integer id) throws SQLException {
        String query = "SELECT * FROM orderitems WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement findAllStatement(Connection connection) throws SQLException {
        String query = "SELECT * FROM orderitems";
        return connection.prepareStatement(query);
    }

    @Override
    public PreparedStatement saveStatement(Connection connection, OrderItems order) throws SQLException {
        String query = "INSERT INTO orderitems (order_id, menuitem_id) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setInt(1, order.getOrder_id());
        statement.setInt(2, order.getMenu_item_id());

        return statement;
    }

    @Override
    public PreparedStatement deleteStatement(Connection connection, Integer id) throws SQLException {
        String query = "DELETE FROM orderitems WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement updateStatement(Connection connection, OrderItems order) throws SQLException {
        String query = "UPDATE orderitems SET order_id=?, menuitem_id=? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, order.getOrder_id());
        statement.setInt(2, order.getMenu_item_id());
        statement.setInt(3, order.getId());
        return statement;
    }



}
