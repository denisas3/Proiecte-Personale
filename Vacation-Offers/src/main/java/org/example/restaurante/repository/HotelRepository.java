package org.example.restaurante.repository;

import org.example.restaurante.domain.Hotel;
import org.example.restaurante.domain.Status;
import org.example.restaurante.domain.validator.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HotelRepository extends AbstractDbRepository<Hotel>{

    public HotelRepository(String url, String username, String password, Validator<Hotel> validator) {
        super(url, username, password, validator);
    }

    @Override
    public Hotel createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_hotel");
        Integer id_locatie  = rs.getInt("id_locatie");
        String hotel_name = rs.getString("hotel_name");
        Integer nr_camere = rs.getInt("nr_camere");
        Double pret_per_noapte = rs.getDouble("pret_pre_noapte");
        Status tip = Status.valueOf(rs.getString("tip"));
        Hotel hotel = new Hotel(id_locatie,hotel_name,nr_camere,pret_per_noapte,tip);
        hotel.setId(id);
        return hotel;
    }

    @Override
    public PreparedStatement findOneStatement(Connection connection, Integer id) throws SQLException {
        String query = "SELECT * FROM hoteluri WHERE id_hotel = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement findAllStatement(Connection connection) throws SQLException {
        String query = "SELECT * FROM hoteluri";
        return connection.prepareStatement(query);
    }

    @Override
    public PreparedStatement saveStatement(Connection connection, Hotel hotel) throws SQLException {
        String query = "INSERT INTO hoteluri (id_locatie, hotel_name, nr_camere,pret_pre_noapte, tip) VALUES (?, ?, ?, ?, ? )";
        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setInt(1, hotel.getId_locatie());
        statement.setString(2, hotel.getHotel_name());
        statement.setInt(3, hotel.getNr_camera());
        statement.setDouble(4, hotel.getPret_per_noapte());
        statement.setString(5, hotel.getTip().name());
        return statement;
    }

    @Override
    public PreparedStatement deleteStatement(Connection connection, Integer id) throws SQLException {
        String query = "DELETE FROM hoteluri WHERE id_hotel = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement updateStatement(Connection connection, Hotel hotel) throws SQLException {
        String query = "UPDATE hoteluri SET id_locatie=?, hotel_name=?, nr_camere=?,pret_pre_noapte=?, tip=? WHERE id_hotel = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, hotel.getId_locatie());
        statement.setString(2, hotel.getHotel_name());
        statement.setInt(3, hotel.getNr_camera());
        statement.setDouble(4, hotel.getPret_per_noapte());
        statement.setString(5, hotel.getTip().name());
        statement.setInt(6, hotel.getId());
        return statement;
    }
}
