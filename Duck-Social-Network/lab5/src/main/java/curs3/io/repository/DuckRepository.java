package curs3.io.repository;

import curs3.io.domain.*;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
public class DuckRepository implements RepositoryBD<Long, Duck> {
    private final Connection conn;

    @Override
    public Optional<Duck> findById(Long duckID) {
        try{

            var statement = conn.prepareStatement(
                    "SELECT d.*, u.username, u.email, u.password\n" +
                            "FROM ducks d\n" +
                            "JOIN users u ON d.userID = u.userID\n" +
                            "WHERE d.duckID = ?");

            statement.setLong(1, duckID);
            var result = statement.executeQuery();
            if(!result.next()) return Optional.empty();

            DuckType type = DuckType.valueOf(result.getString("type").toUpperCase());

            Duck duck = createDuckFromResult(result, type);

            return Optional.of(duck);

        }catch(SQLException e){
            throw new RuntimeException(e);
        }

    }

    private Duck createDuckFromResult(ResultSet result, DuckType type) throws SQLException {

        Long duckID = result.getLong("duckID");
        Long userID = result.getLong("userID");
        String username = result.getString("username");
        String email = result.getString("email");
        String password = result.getString("password");
        double speed = result.getDouble("speed");
        double resistance = result.getDouble("resistance");

        return switch (type) {
            case FLYING -> new FlyingDuck(
                    duckID, userID, username, email, password, speed, resistance, DuckType.FLYING
            );
            case SWIMMING -> new SwimmingDuck(
                    duckID, userID, username, email, password, speed, resistance, DuckType.SWIMMING
            );
            case FLYING_AND_SWIMMING -> null;
        };
    }

    @Override
    public Optional<Duck> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Optional<Duck> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Iterable<Duck> getAll() {
        try{
            var statement = conn.prepareStatement(
                    "SELECT d.*, u.username, u.email, u.password " +
                            "FROM ducks d " +
                            "JOIN users u ON d.userID = u.userID"
            );

            var result = statement.executeQuery();
            Set<Duck> ducks = new LinkedHashSet<>();
            while (result.next()) {

                DuckType type = DuckType.valueOf(result.getString("type").toUpperCase());

                Duck duck = createDuckFromResult(result, type);

                ducks.add(duck);
            }

            return ducks;

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Duck> save(Duck duck) {
        try{

            var statemant = conn.prepareStatement(
                    "INSERT INTO ducks(speed,resistance,type, userID)" +
                            " VALUES (?,?,?,?)",
                            Statement.RETURN_GENERATED_KEYS
            );

            statemant.setDouble(1, duck.getSpeed());
            statemant.setDouble(2, duck.getResistance());
            statemant.setString(3, duck.getType().name());
            statemant.setLong(4, duck.getId());

            statemant.executeUpdate();

            ResultSet keys = statemant.getGeneratedKeys();
            if (keys.next())
                duck.setDuckID(keys.getLong(1));

            return Optional.empty();

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Duck> update(Duck duck) {

        try{

            var stmtUser = conn.prepareStatement(
                    "UPDATE users SET username=?, email=?, password=? WHERE userID=?"
            );

            stmtUser.setString(1, duck.getUsername());
            stmtUser.setString(2, duck.getEmail());
            stmtUser.setString(3, duck.getPassword());
            stmtUser.setLong(4, duck.getId());

            int rowsUser = stmtUser.executeUpdate();
            if (rowsUser == 0) {
                return Optional.empty();
            }

            var stmtDuck = conn.prepareStatement(
                    "UPDATE ducks SET speed=?, resistance=?, type=? WHERE duckID=?"
            );

            stmtDuck.setDouble(1, duck.getSpeed());
            stmtDuck.setDouble(2, duck.getResistance());
            stmtDuck.setString(3, duck.getType().name());
            stmtDuck.setLong(4, duck.getDuckID());

            int rowsDuck = stmtDuck.executeUpdate();
            if (rowsDuck == 0) {
                return Optional.empty();
            }

            return Optional.of(duck);


        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Duck> delete(Long duckID) {
        try{

            var duck = findById(duckID);

            if(duck.isEmpty())
                return Optional.empty();

            var statement = conn.prepareStatement("DELETE FROM ducks WHERE duckid=?");

            statement.setLong(1,duckID);

            var result = statement.executeUpdate();

            if (result != 0){
                return duck;
            }
            else return Optional.empty();

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<Duck> findAll(RepositoryPaging paging) {
        try{
            int offset = paging.getPageNumber() * paging.getPageSize();
            int pageSize = paging.getPageSize();

            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT d.*, u.username, u.email, u.password " +
                            "FROM ducks d " +
                            "JOIN users u ON d.userID = u.userID " +
                            "ORDER BY d.duckID " +
                            "LIMIT ? OFFSET ?"
            );

            stmt.setInt(1, pageSize);
            stmt.setInt(2, offset);

            ResultSet rs = stmt.executeQuery();

            List<Duck> ducks = new ArrayList<>();

            while (rs.next()) {
                DuckType type = DuckType.valueOf(rs.getString("type").toUpperCase());
                ducks.add(createDuckFromResult(rs, type));
            }

            PreparedStatement countStmt = conn.prepareStatement(
                    "SELECT COUNT(*) FROM ducks"
            );
            ResultSet countRs = countStmt.executeQuery();
            countRs.next();
            int total = countRs.getInt(1);

            return new Page<>(ducks, total);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Duck> findPage(int offset, int pageSize) {
        try {
            var stmt = conn.prepareStatement(
                    "SELECT d.*, u.username, u.email, u.password " +
                            "FROM ducks d " +
                            "JOIN users u ON d.userID = u.userID " +
                            "ORDER BY d.duckID " +
                            "LIMIT ? OFFSET ?"
            );

            stmt.setInt(1, pageSize);
            stmt.setInt(2, offset);

            var rs = stmt.executeQuery();

            List<Duck> list = new ArrayList<>();

            while (rs.next()) {
                DuckType type = DuckType.valueOf(rs.getString("type").toUpperCase());
                Duck duck = createDuckFromResult(rs, type);
                list.add(duck);
            }

            return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int countDucks() {
        try {
            var stmt = conn.prepareStatement("SELECT COUNT(*) FROM ducks");
            var rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Duck> getPageFiltered(DuckType type, int page, int size) {
        List<Duck> list = new ArrayList<>();

        String sql;

        if (type == null) {
            sql = """
            SELECT d.*, u.username, u.email, u.password
            FROM ducks d
            JOIN users u ON d.userID = u.userID
            ORDER BY d.duckID
            LIMIT ? OFFSET ?
        """;
        } else {
            sql = """
            SELECT d.*, u.username, u.email, u.password
            FROM ducks d
            JOIN users u ON d.userID = u.userID
            WHERE d.type = ?
            ORDER BY d.duckID
            LIMIT ? OFFSET ?
        """;
        }

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            if (type == null) {
                ps.setInt(1, size);
                ps.setInt(2, page * size);
            } else {
                ps.setString(1, type.name());
                ps.setInt(2, size);
                ps.setInt(3, page * size);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DuckType dt = DuckType.valueOf(rs.getString("type").toUpperCase());
                Duck duck = createDuckFromResult(rs, dt);
                list.add(duck);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error loading filtered page of ducks", e);
        }

        return list;
    }

    public List<Duck> getPage(DuckType type, int page, int size) throws SQLException {

        String sql = (type == null)
                ? "SELECT d.*, u.username, u.email, u.password " +
                "FROM ducks d JOIN users u ON d.userID = u.userID " +
                "ORDER BY d.duckID LIMIT ? OFFSET ?"
                : "SELECT d.*, u.username, u.email, u.password " +
                "FROM ducks d JOIN users u ON d.userID = u.userID " +
                "WHERE d.type = ? ORDER BY d.duckID LIMIT ? OFFSET ?";

        PreparedStatement ps = conn.prepareStatement(sql);

        if (type == null) {
            ps.setInt(1, size);
            ps.setInt(2, page * size);
        } else {
            ps.setString(1, type.name());
            ps.setInt(2, size);
            ps.setInt(3, page * size);
        }

        ResultSet rs = ps.executeQuery();
        List<Duck> list = new ArrayList<>();

        while (rs.next()) {
            DuckType dt = DuckType.valueOf(rs.getString("type").toUpperCase());
            Duck duck = createDuckFromResult(rs, dt);
            list.add(duck);
        }

        return list;
    }

    public int countFiltered(DuckType type) {
        String sql = type == null ?
                "SELECT COUNT(*) FROM ducks" :
                "SELECT COUNT(*) FROM ducks WHERE ducktype = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            if (type != null)
                ps.setString(1, type.name());

            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
