package org.example.lab03.repository;

import org.example.lab03.domain.Employee;

public interface EmployeeRepository extends Repository<Long, Employee> {

    Employee findByUsername(String username);

    Employee delete(Long integer);

    Employee findByUsernameAndPassword(String username, String password);

}
