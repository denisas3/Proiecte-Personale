package org.example.lab03.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.lab03.domain.Show;
import org.example.lab03.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

public class ShowDbRepository implements ShowRepository {

    private JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public ShowDbRepository(JdbcUtils dbUtils) {
        logger.info("Initializing ShowDbRepository with properties: {} ", dbUtils);
        this.dbUtils = dbUtils;
    }

    @Override
    public List<Show> findByDate(LocalDateTime date) {
        logger.traceEntry();
        List<Show> shows = new ArrayList<>();
        Connection conn = dbUtils.getConnection();

        LocalDateTime start = date.toLocalDate().atStartOfDay();
        LocalDateTime end = date.toLocalDate().atTime(23, 59, 59);

        try(PreparedStatement preStmt = conn.prepareStatement(
                "select * from shows where date >= ? and date <= ?"
        )) {

            preStmt.setString(1, start.toString());
            preStmt.setString(2, end.toString());

            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    long id = result.getLong("id_show");
                    String artistName = result.getString("artistName");
                    LocalDateTime date1 = LocalDateTime.parse(result.getString("date"));
                    String location = result.getString("location");
                    Integer availableSeats = result.getInt("availableSeats");
                    Integer soldSeats = result.getInt("soldSeats");

                    Show show = new Show(artistName, date1, location, availableSeats, soldSeats);
                    show.setId(id);
                    shows.add(show);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit(shows);
        return shows;
    }

    @Override
    public List<Show> findByArtist(String artist) {
        logger.traceEntry();
        List<Show> shows = new ArrayList<>();
        Connection conn = dbUtils.getConnection();
        try(PreparedStatement preStmt = conn.prepareStatement(
                "select * from shows where artistName = ?"
        )) {

            preStmt.setString(1, artist);

            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    Long id = result.getLong("id_show");
                    String artistName = result.getString("artistName");
                    LocalDateTime date = LocalDateTime.parse(result.getString("date"));
                    String location = result.getString("location");
                    Integer availableSeats = result.getInt("availableSeats");
                    Integer soldSeats = result.getInt("soldSeats");

                    Show show = new Show(artistName, date, location, availableSeats, soldSeats);
                    show.setId(id);
                    shows.add(show);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit(shows);
        return shows;
    }

    @Override
    public Show findOne(Long integer) {
        logger.traceEntry();
        Show show = null;
        Connection conn = dbUtils.getConnection();
        try(PreparedStatement preStmt = conn.prepareStatement("select * from shows where id_show=?")){

            preStmt.setLong(1, integer);

            try(ResultSet result = preStmt.executeQuery()){
                if(result.next()){
                    long id = result.getLong("id_show");
                    String artistName = result.getString("artistName");
                    LocalDateTime date = LocalDateTime.parse(result.getString("date"));
                    String location = result.getString("location");
                    Integer availableSeats = result.getInt("availableSeats");
                    Integer soldSeats = result.getInt("soldSeats");

                    show = new Show(artistName,date,location,availableSeats,soldSeats);
                    show.setId(id);
                }
            }
        }catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit(show);
        return show;
    }

    @Override
    public Iterable<Show> findAll() {
        logger.traceEntry();
        List<Show> shows = new ArrayList<>();
        Connection conn = dbUtils.getConnection();
        try(PreparedStatement preStmt = conn.prepareStatement(
                "select * from shows"
        )) {
            try(ResultSet result = preStmt.executeQuery();) {
                while (result.next()) {
                    long id = result.getLong("id_show");
                    String artistName = result.getString("artistName");
                    LocalDateTime date = LocalDateTime.parse(result.getString("date"));
                    String location = result.getString("location");
                    Integer availableSeats = result.getInt("availableSeats");
                    Integer soldSeats = result.getInt("soldSeats");

                    Show show = new Show(artistName, date, location, availableSeats, soldSeats);
                    show.setId(id);
                    shows.add(show);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit(shows);
        return shows;
    }

    /// returneaza null la succes si entitatea la insucces
    @Override
    public Show save(Show entity) {
        logger.traceEntry("saving show {}", entity);
        Connection conn = dbUtils.getConnection();
        try (PreparedStatement preStmt = conn.prepareStatement(
                "insert into shows (artistName, date, location, availableSeats, soldSeats) values (?,?,?,?,?)"
        )) {
            preStmt.setString(1, entity.getArtistName());
            preStmt.setString(2, entity.getDate().toString());
            preStmt.setString(3, entity.getLocation());
            preStmt.setInt(4, entity.getAvailableSeats());
            preStmt.setInt(5, entity.getSoldSeats());

            int result = preStmt.executeUpdate();
            if(result < 1){
                throw new RuntimeException("Error saving show" +  entity);
            }
            logger.trace("Saved {} instance", result);
            logger.traceExit();
            return null;

        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
            logger.traceExit();
            return entity;
        }
    }

    @Override
    public Show delete(Long integer) {
        return null;
    }

    @Override
    public Show update(Show entity) {
        logger.traceEntry("update show {}", entity);
        Connection conn = dbUtils.getConnection();
        try (PreparedStatement preStmt = conn.prepareStatement(
                "update shows set artistName=?, date=?, location=?, availableSeats=?, soldSeats=? where id_show=?"
        )) {
            preStmt.setString(1, entity.getArtistName());
            preStmt.setString(2, entity.getDate().toString());
            preStmt.setString(3, entity.getLocation());
            preStmt.setInt(4, entity.getAvailableSeats());
            preStmt.setInt(5, entity.getSoldSeats());
            preStmt.setLong(6, entity.getId());

            int result = preStmt.executeUpdate();
            logger.traceEntry("Updated {} instance", result);

            logger.traceExit();
            return null;

        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
            logger.traceExit();
            return entity;
        }
    }
}

