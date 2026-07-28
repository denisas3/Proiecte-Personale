package org.example.lab03.services;

import org.example.lab03.Employee;

public interface IEmployeeService {
    Employee login(String username, String password) throws Exception;
}
