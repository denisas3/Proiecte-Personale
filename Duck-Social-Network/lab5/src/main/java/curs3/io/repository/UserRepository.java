package curs3.io.repository;

import curs3.io.domain.Persoana;
import curs3.io.domain.Utilizator;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class UserRepository implements RepositoryBD<Long, Utilizator>{
    private final Connection conn;

    @Override
    public Optional<Utilizator> findById(Long userId) {
        try{

            var statement = conn.prepareStatement("SELECT * FROM users WHERE userId=?");

            statement.setLong(1, userId);
            var result = statement.executeQuery();

            if(result.next()){
                String username = result.getString("username");
                String email = result.getString("email");
                String password = result.getString("password");

                var user = new Utilizator(userId,username,email,password);
                user.setId(userId);
                return Optional.of(user);

            }
            return Optional.empty();

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Optional<Utilizator> findByUsername(String usernameSearch) {
        try{

            var statement = conn.prepareStatement(
                    "SELECT * FROM users WHERE username = ?"
            );

            statement.setString(1, usernameSearch);

            var result = statement.executeQuery();
            if (!result.next()) return Optional.empty();

            Utilizator u = new Utilizator(
                    result.getLong("userid"),
                    result.getString("username"),
                    result.getString("email"),
                    result.getString("password")
            );
            return Optional.of(u);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Utilizator> findByEmail(String emailSearch) {
        try{

            var statement = conn.prepareStatement(
                    "SELECT * FROM users WHERE email = ?"
            );

            statement.setString(1, emailSearch);

            var result = statement.executeQuery();
            if (!result.next()) return Optional.empty();

            Utilizator u = new Utilizator(
                    result.getLong("userid"),
                    result.getString("username"),
                    result.getString("email"),
                    result.getString("password")
            );
            return Optional.of(u);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Iterable<Utilizator> getAll() {
        try{

            var statement = conn.prepareStatement("SELECT * FROM users");
            var result = statement.executeQuery();

            Set<Utilizator> users = new LinkedHashSet<>();
            while(result.next()){
                long userID = result.getLong("userId");
                String username = result.getString("username");
                String email = result.getString("email");
                String password = result.getString("password");

                var user = new Utilizator(userID,username,email,password);
                user.setId(userID);
                users.add(user);
            }
            return users;

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Utilizator> save(Utilizator user) {
        try{

            var stmt = conn.prepareStatement(
                    "INSERT INTO users(username, email, password) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());

            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next())
                user.setId(keys.getLong(1));

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    public Optional<Utilizator> save(Utilizator user) {
//        try(var conn = DriverManager.getConnection(url, username, password)){
//
//            var statemant = conn.prepareStatement("INSERT INTO users(userID,username,email,password) VALUES (?,?,?,?)");
//
//            statemant.setLong(1, user.getId());
//            statemant.setString(2, user.getUsername());
//            statemant.setString(3, user.getEmail());
//            statemant.setString(4, user.getPassword());
//            var result = statemant.executeUpdate();
//
//            if(result == 0 ){
//                return Optional.of(user);
//            }
//            else return Optional.empty();
//
//        }catch(SQLException e){
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public Optional<Utilizator> update(Utilizator user) {
        try{

            var statement = conn.prepareStatement("UPDATE users SET username=?,email=?, password=? WHERE userID=?");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setLong(4, user.getId());

            var result = statement.executeUpdate();

            if (result == 0){
                return Optional.empty();
            }
            else {
                return Optional.of(user);
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Utilizator> delete(Long userID) {
        try{

            var user = findById(userID);

            if(user.isEmpty())
                return Optional.empty();

            var statement = conn.prepareStatement("DELETE FROM users WHERE userID=?");

            statement.setLong(1,userID);

            var result = statement.executeUpdate();

            if (result != 0){
                return user;
            }
            else return Optional.empty();

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<Utilizator> findAll(RepositoryPaging paging) {
        return null;
    }

    @Override
    public List<Utilizator> findPage(int offset, int pageSize) {
        return List.of();
    }

    @Override
    public int countDucks() {
        return 0;
    }


}
