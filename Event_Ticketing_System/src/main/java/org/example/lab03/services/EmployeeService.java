package org.example.lab03.services;

import org.example.lab03.Employee;
import org.example.lab03.repository.EmployeeRepository;

public class EmployeeService implements IEmployeeService{
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee login(String username, String password) throws Exception {
        Employee employee = employeeRepository.findByUsername(username);

        if(employee == null){
            throw new Exception("Invalid user!");
        }

        if(!employee.getPassword().equals(password)){
            throw new Exception("Incorect data!");
        }

        return employee;
    }
}
