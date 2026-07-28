package org.example.lab03.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.lab03.domain.Show;
import org.example.lab03.domain.Ticket;
import org.example.lab03.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TicketDbRepository implements TicketRepository {

    private JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public TicketDbRepository(JdbcUtils dbUtils) {
        logger.info("Initializing TicketDbRepository with properties: {} ", dbUtils);
        this.dbUtils = dbUtils;
    }

    @Override
    public List<Ticket> findByShow(Show show) {
        logger.traceEntry();
        Ticket ticket = null;
        List<Ticket> tickets = new ArrayList<>();
        Connection conn = dbUtils.getConnection();
        try(PreparedStatement preStmt = conn.prepareStatement("select * from tickets t join shows s on t.id_show=s.id_show where t.id_show = ?;");){

            preStmt.setLong(1, show.getId());

            try(ResultSet result = preStmt.executeQuery()){
                while (result.next()){
                    long id = result.getLong("id_ticket");
                    String buyerName = result.getString("buyerName");
                    Integer seatCount = result.getInt("seatCount");
                    LocalDateTime soldAt = LocalDateTime.parse(result.getString("soldAt"));

                    long id_show = result.getLong("id_show");
                    String artistName = result.getString("artistName");
                    LocalDateTime date = LocalDateTime.parse(result.getString("date"));
                    String location = result.getString("location");
                    Integer availableSeats = result.getInt("availableSeats");
                    Integer soldSeats = result.getInt("soldSeats");

                    Show show1 = new Show(artistName,date,location,availableSeats,soldSeats);
                    show1.setId(id_show);

                    ticket = new Ticket(buyerName,seatCount,soldAt,show1);
                    ticket.setId(id);
                    tickets.add(ticket);
                }
            }

        }catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit(tickets);
        return tickets;
    }

    @Override
    public Ticket findOne(Long integer) {
        logger.traceEntry();
        Ticket ticket = null;
        Connection conn = dbUtils.getConnection();
        try(PreparedStatement preStmt = conn.prepareStatement("select * from tickets t join shows s on t.id_show=s.id_show where t.id_ticket=?")){

            preStmt.setLong(1, integer);

            try(ResultSet result = preStmt.executeQuery()){
                if(result.next()){
                    long id = result.getLong("id_ticket");
                    String buyerName = result.getString("buyerName");
                    Integer seatCount = result.getInt("seatCount");
                    LocalDateTime soldAt = LocalDateTime.parse(result.getString("soldAt"));

                    long id_show = result.getLong("id_show");
                    String artistName = result.getString("artistName");
                    LocalDateTime date = LocalDateTime.parse(result.getString("date"));
                    String location = result.getString("location");
                    Integer availableSeats = result.getInt("availableSeats");
                    Integer soldSeats = result.getInt("soldSeats");

                    Show show = new Show(artistName,date,location,availableSeats,soldSeats);
                    show.setId(id_show);

                    ticket = new Ticket(buyerName,seatCount,soldAt,show);
                    ticket.setId(id);
                }
            }

        }catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit(ticket);
        return ticket;
    }

    @Override
    public Iterable<Ticket> findAll() {
        logger.traceEntry();
        List<Ticket> tickets = new ArrayList<>();
        Connection conn = dbUtils.getConnection();
        try(PreparedStatement preStmt = conn.prepareStatement(
                "select * from tickets t join  shows s on t.id_show=s.id_show"
        )) {
            try(ResultSet result = preStmt.executeQuery();) {
                while (result.next()) {
                    long id = result.getLong("id_ticket");
                    String buyerName = result.getString("buyerName");
                    Integer seatCount = result.getInt("seatCount");
                    LocalDateTime soldAt = LocalDateTime.parse(result.getString("soldAt"));

                    long id_show = result.getLong("id_show");
                    String artistName = result.getString("artistName");
                    LocalDateTime date = LocalDateTime.parse(result.getString("date"));
                    String location = result.getString("location");
                    Integer availableSeats = result.getInt("availableSeats");
                    Integer soldSeats = result.getInt("soldSeats");

                    Show show = new Show(artistName,date,location,availableSeats,soldSeats);
                    show.setId(id_show);

                    Ticket ticket = new Ticket(buyerName,seatCount,soldAt,show);
                    ticket.setId(id);
                    tickets.add(ticket);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit(tickets);
        return tickets;
    }

    /// returneaza null la succes si entitatea la insucces
    @Override
    public Ticket save(Ticket entity) {
        logger.traceEntry("saving ticket {}", entity);
        Connection conn = dbUtils.getConnection();
        try (PreparedStatement preStmt = conn.prepareStatement(
                "insert into tickets (buyerName, seatCount, soldAt, id_show) values (?,?,?,?)"
        )) {
            preStmt.setString(1, entity.getBuyerName());
            preStmt.setInt(2, entity.getSeatCount());
            preStmt.setString(3, entity.getSoldAt().toString());
            preStmt.setLong(4, entity.getShow().getId());

            int result = preStmt.executeUpdate();
            if(result < 1){
                throw new RuntimeException("Error inserting ticket" +  entity);
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
    public Ticket delete(Long integer) {
        return null;
    }

    @Override
    public Ticket update(Ticket entity) {
        logger.traceEntry("update ticket {}", entity);
        Connection conn = dbUtils.getConnection();
        try (PreparedStatement preStmt = conn.prepareStatement(
                "update tickets set buyerName=?, seatCount=?, soldAt=?, id_show=? where id_ticket=?"
        )) {
            preStmt.setString(1, entity.getBuyerName());
            preStmt.setInt(2, entity.getSeatCount());
            preStmt.setString(3, entity.getSoldAt().toString());
            preStmt.setLong(4, entity.getShow().getId());
            preStmt.setLong(5, entity.getId());

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

