package org.example.restaurante.repository;

import org.example.restaurante.domain.Order;
import org.example.restaurante.domain.OrderStatus;
import org.example.restaurante.domain.validator.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class OrderRepository extends AbstractDbRepository<Order>{

    public OrderRepository(String url, String username, String password, Validator<Order> validator){
        super(url, username, password, validator);
    }

    @Override
    public Order createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("order_id");
        Integer id_table = rs.getInt("id_table");
        LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();
        OrderStatus status = OrderStatus.valueOf(rs.getString("status"));
        Order order = new Order(id_table,date,status);
        order.setId(id);
        return order;
    }

    @Override
    public PreparedStatement findOneStatement(Connection connection, Integer id) throws SQLException {
        String query = "SELECT * FROM orders WHERE order_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement findAllStatement(Connection connection) throws SQLException {
        String query = "SELECT * FROM orders";
        return connection.prepareStatement(query);
    }

    @Override
    public PreparedStatement saveStatement(Connection connection, Order order) throws SQLException {
        String query = "INSERT INTO orders (id_table, date, status) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setInt(1, order.getTable());
        statement.setTimestamp(2, java.sql.Timestamp.valueOf(order.getDate()));
        statement.setString(3,order.getStatus().toString());
        return statement;
    }

    @Override
    public PreparedStatement deleteStatement(Connection connection, Integer id) throws SQLException {
        String query = "DELETE FROM orders WHERE order_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement updateStatement(Connection connection, Order order) throws SQLException {
        String query = "UPDATE orders SET id_table=?, date=?, status=? WHERE order_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, order.getTable());
        statement.setTimestamp(2, java.sql.Timestamp.valueOf(order.getDate()));
        statement.setString(3, order.getStatus().name());
        statement.setInt(4, order.getId());
        return statement;
    }



}
