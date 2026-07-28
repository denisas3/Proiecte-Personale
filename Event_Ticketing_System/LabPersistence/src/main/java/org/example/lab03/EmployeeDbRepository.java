//package org.example.lab03;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.example.lab03.Employee;
//import org.example.lab03.repository.EmployeeRepository;
//import org.example.lab03.utils.JdbcUtils;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class EmployeeDbRepository implements EmployeeRepository {
//
//    private final JdbcUtils dbUtils;
//
//    private static final Logger logger = LogManager.getLogger();
//
//    public EmployeeDbRepository(JdbcUtils dbUtils) {
//        logger.info("Initializing EmployeeDBRepository with properties: {} ", dbUtils);
//        this.dbUtils=dbUtils;
//    }
//
//    @Override
//    public Employee findByUsername(String username) {
//        logger.traceEntry();
//        Employee employee = null;
//        Connection conn = dbUtils.getConnection();
//        try(PreparedStatement preStmt = conn.prepareStatement("select * from employees where username=?")){
//
//            preStmt.setString(1, username);
//
//            try(ResultSet result = preStmt.executeQuery()){
//                if(result.next()){
//                    int id = result.getInt("id_employee");
//                    String username1 = result.getString("username");
//                    String password = result.getString("password");
//                    String name = result.getString("name");
//
//                    employee = new Employee(username1, password, name);
//                    employee.setId(id);
//                }
//            }
//        }catch (SQLException ex) {
//            logger.error(ex);
//            System.err.println("Error DB " + ex);
//        }
//        logger.traceExit(employee);
//        return employee;
//    }
//
//    @Override
//    public Employee findOne(Integer integer) {
//        logger.traceEntry();
//        Employee employee = null;
//        Connection conn = dbUtils.getConnection();
//        try(PreparedStatement preStmt = conn.prepareStatement("select * from employees where id_employee=?")){
//
//            preStmt.setInt(1, integer);
//
//            try(ResultSet result = preStmt.executeQuery()){
//                if(result.next()){
//                    int id = result.getInt("id_employee");
//                    String username = result.getString("username");
//                    String password = result.getString("password");
//                    String name = result.getString("name");
//
//                    employee = new Employee(username, password, name);
//                    employee.setId(id);
//                }
//            }
//
//        }catch (SQLException ex) {
//            logger.error(ex);
//            System.err.println("Error DB " + ex);
//        }
//        logger.traceExit(employee);
//        return employee;
//    }
//
//    @Override
//    public Iterable<Employee> findAll() {
//        logger.traceEntry();
//        List<Employee> employees = new ArrayList<>();
//        Connection conn = dbUtils.getConnection();
//        try(PreparedStatement preStmt = conn.prepareStatement(
//                    "select * from employees"
//            )) {
//            try(ResultSet result = preStmt.executeQuery();) {
//                while (result.next()) {
//                    int id = result.getInt("id_employee");
//                    String username = result.getString("username");
//                    String password = result.getString("password");
//                    String name = result.getString("name");
//                    Employee employee = new Employee(username, password, name);
//                    employee.setId(id);
//                    employees.add(employee);
//                }
//            }
//        } catch (SQLException ex) {
//            logger.error(ex);
//            System.err.println("Error DB " + ex);
//        }
//        logger.traceExit(employees);
//        return employees;
//    }
//
//
//    @Override
//    public Employee save(Employee entity) {
//        logger.traceEntry("saving employee {}", entity);
//        Connection conn = dbUtils.getConnection();
//        try (PreparedStatement preStmt = conn.prepareStatement(
//                     "insert into employees (username, password, name) values (?,?,?)"
//             )) {
//            preStmt.setString(1, entity.getUsername());
//            preStmt.setString(2, entity.getPassword());
//            preStmt.setString(3, entity.getName());
//
//            int result = preStmt.executeUpdate();
//            logger.trace("Saved {} instance", result);
//            logger.traceExit();
//            return null;
//
//        } catch (SQLException ex) {
//            logger.error(ex);
//            System.err.println("Error DB " + ex);
//            logger.traceExit();
//            return entity;
//        }
//    }
//
//    @Override
//    public Employee delete(Integer integer) {
//        return null;
//    }
//
//    @Override
//    public Employee update(Employee entity) {
//        logger.traceEntry("update employee {}", entity);
//        Connection conn = dbUtils.getConnection();
//        try (PreparedStatement preStmt = conn.prepareStatement(
//                     "update employees set username=?, password=?, name=? where id_employee=?"
//             )) {
//            preStmt.setString(1, entity.getUsername());
//            preStmt.setString(2, entity.getPassword());
//            preStmt.setString(3, entity.getName());
//            preStmt.setInt(4, entity.getId());
//
//            int result = preStmt.executeUpdate();
//            logger.traceEntry("Updated {} employee", result);
//
//            logger.traceExit();
//            return null;
//
//        } catch (SQLException ex) {
//            logger.error(ex);
//            System.err.println("Error DB " + ex);
//            logger.traceExit();
//            return entity;
//        }
//    }
//}