package curs3.io.repository;

import curs3.io.domain.Persoana;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.*;

@RequiredArgsConstructor
public class PersoanaRepository implements RepositoryBD<Long, Persoana> {

    private final Connection conn;

    @Override
    public Optional<Persoana> save(Persoana p) {
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO persoane(userID, lastname, firstname, jobtitle, birthdate, empatylevel) " +
                            "VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            stmt.setLong(1, p.getUserID());
            stmt.setString(2, p.getLastName());
            stmt.setString(3, p.getFirstName());
            stmt.setString(4, p.getJobTitle());
            stmt.setDate(5, java.sql.Date.valueOf(p.getBirthDate()));
            stmt.setInt(6, p.getEmpathyLevel());

            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                p.setId(keys.getLong(1)); // persoanaID
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Cannot save persoana: " + e.getMessage());
        }
    }

    @Override
    public Iterable<Persoana> getAll() {
        List<Persoana> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT p.*, u.username, u.email, u.password " +
                            "FROM persoane p JOIN users u ON p.userID = u.userID"
            );

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Persoana p = new Persoana(
                        rs.getLong("persoanaID"),
                        rs.getLong("userID"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("lastname"),
                        rs.getString("firstname"),
                        rs.getString("jobtitle"),
                        rs.getDate("birthdate").toLocalDate(),
                        rs.getInt("empatylevel")
                );

                lista.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Cannot load persoane: " + e.getMessage());
        }
        return lista;
    }

    @Override public Optional<Persoana> findById(Long id) { return Optional.empty(); }
    @Override public Optional<Persoana> update(Persoana entity) { return Optional.empty(); }
    @Override public Optional<Persoana> delete(Long id) { return Optional.empty(); }
    @Override public Optional<Persoana> findByUsername(String s) { return Optional.empty(); }
    @Override public Optional<Persoana> findByEmail(String s) { return Optional.empty(); }
    @Override public Page<Persoana> findAll(RepositoryPaging paging) { return null; }
    @Override public List<Persoana> findPage(int offset, int pageSize) { return List.of(); }
    @Override public int countDucks() { return 0; }
}
