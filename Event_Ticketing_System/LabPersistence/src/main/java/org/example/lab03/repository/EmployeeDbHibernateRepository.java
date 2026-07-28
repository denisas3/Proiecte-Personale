package org.example.lab03.repository;

import org.example.lab03.domain.Employee;
import org.example.lab03.utils.EncryptUtils;
import org.hibernate.Session;

import java.util.List;
import java.util.Objects;


public class EmployeeDbHibernateRepository implements EmployeeRepository{

    @Override
    public Employee save(Employee employee){
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(employee));
        return employee;
    }

    @Override
    public Employee findOne(Long id){
        try (Session session = HibernateUtils.getSessionFactory().openSession()){
            return session.createSelectionQuery("from Employee where id=:id", Employee.class)
                    .setParameter("id", id)
                    .getSingleResultOrNull();
        }
    }

    @Override
    public List<Employee> findAll(){
        try (Session session = HibernateUtils.getSessionFactory().openSession()){
            return session.createQuery("from Employee", Employee.class).getResultList();
        }
    }

    @Override
    public Employee update(Employee employee){
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            if(!Objects.isNull(session.find(Employee.class, employee.getId()))){
                System.out.println("In update, am gasit employeeul cu id-ul "+employee.getId());
                session.merge(employee);
                session.flush();

            }
        });
        return employee;
    }

    @Override
    public Employee findByUsernameAndPassword(String username, String password){
//        String encryptedPassword = EncryptUtils.encryptPassword(password);

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery(
                            "from Employee e where e.username = :username and e.password = :password",
                            Employee.class
                    )
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResultOrNull();
        }
    }

    @Override
    public Employee findByUsername(String username){
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery("from Employee e where e.username = : username", Employee.class)
                    .setParameter("username", username)
                    .getSingleResultOrNull();
        }
    }

    @Override
    public Employee delete(Long integer) {
        return null;
    }
}
