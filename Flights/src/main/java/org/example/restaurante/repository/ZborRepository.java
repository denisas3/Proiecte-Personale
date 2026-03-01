package org.example.restaurante.repository;

import org.example.restaurante.domain.Zbor;
import org.example.restaurante.domain.validator.Validator;
import org.example.restaurante.utils.paging.Page;
import org.example.restaurante.utils.paging.Pageable;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ZborRepository extends AbstractDbRepository<Zbor>{

    public ZborRepository(String url, String username, String password, Validator<Zbor> validator) {
        super(url, username, password, validator);
    }

    @Override
    public Zbor createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_zbor");
        String de_unde = rs.getString("de_unde");
        String pana_unde = rs.getString("pana_unde");
        LocalDateTime decolare = rs.getObject("decolare", LocalDateTime.class);
        LocalDateTime aterizare = rs.getObject("aterizare", LocalDateTime.class);
        Integer loc = rs.getInt("loc");
        Zbor zbor = new Zbor(de_unde, pana_unde, decolare, aterizare, loc);
        zbor.setId(id);
        return zbor;
    }

    @Override
    public PreparedStatement findOneStatement(Connection connection, Integer id) throws SQLException {
        String query = "SELECT * FROM zboruri WHERE id_zbor = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement findAllStatement(Connection connection) throws SQLException {
        String query = "SELECT * FROM zboruri";
        return connection.prepareStatement(query);
    }

    @Override
    public PreparedStatement saveStatement(Connection connection, Zbor zbor) throws SQLException {
        String query = "INSERT INTO zboruri (de_unde, pana_unde, decolare,aterizare,loc) VALUES (?, ?, ?, ?, ? )";
        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setString(1, zbor.getDe_unde());
        statement.setString(2, zbor.getPana_unde());
        statement.setObject(3, zbor.getDecolare());
        statement.setObject(4, zbor.getAterizare());
        statement.setInt(5, zbor.getLoc());
        return statement;
    }

    @Override
    public PreparedStatement deleteStatement(Connection connection, Integer id) throws SQLException {
        String query = "DELETE FROM zboruri WHERE id_zboruri = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public PreparedStatement updateStatement(Connection connection, Zbor zbor) throws SQLException {
        String query = "UPDATE zboruri SET de_unde=?, pana_unde=?, decolare=?,aterizare=?,loc=? WHERE id_zbor = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, zbor.getDe_unde());
        statement.setString(2, zbor.getPana_unde());
        statement.setObject(3, zbor.getDecolare());
        statement.setObject(4, zbor.getAterizare());
        statement.setInt(5, zbor.getLoc());
        statement.setInt(6, zbor.getId());
        return statement;
    }


    /// PENTRU PAGINARE
    public Page<Zbor> findPageFiltered(String from, String to, LocalDate date, Pageable pageable) {

        String where = " WHERE 1=1 ";
        if (from != null) where += " AND de_unde = ? ";
        if (to != null) where += " AND pana_unde = ? ";
        if (date != null) where += " AND DATE(decolare) = ? ";

        String countSql = "SELECT COUNT(*) FROM zboruri " + where;
        String pageSql  = "SELECT * FROM zboruri " + where +
                " ORDER BY decolare LIMIT ? OFFSET ?";

        try (Connection c = DriverManager.getConnection(url, username, password)) {
            int total;
            try (PreparedStatement ps = c.prepareStatement(countSql)) {
                int idx = 1;
                if (from != null) ps.setString(idx++, from);
                if (to != null) ps.setString(idx++, to);
                if (date != null) ps.setObject(idx++, date);

                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    total = rs.getInt(1);
                }
            }

            java.util.List<Zbor> list = new java.util.ArrayList<>();
            try (PreparedStatement ps = c.prepareStatement(pageSql)) {
                int idx = 1;
                if (from != null) ps.setString(idx++, from);
                if (to != null) ps.setString(idx++, to);
                if (date != null) ps.setObject(idx++, date);

                ps.setInt(idx++, pageable.getPageSize());
                ps.setInt(idx, pageable.getPageNumber() * pageable.getPageSize());

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        list.add(createEntity(rs));
                    }
                }
            }

            return new Page<>(list, total);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
