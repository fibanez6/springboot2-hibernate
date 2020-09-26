package com.fibanez.springboot.service;

import com.fibanez.springboot.exception.RecordNotFoundException;
import com.fibanez.springboot.model.Employee;

import java.util.List;

public interface EmployeeService {
     

    List<Employee> getAllEmployees();
     
    Employee getEmployeeById(Long id) throws RecordNotFoundException;

    Employee getEmployeeByFirstName(String firstName) throws RecordNotFoundException;

    Employee createOrUpdateEmployee(Employee entity) throws RecordNotFoundException;
     
    void deleteEmployeeById(Long id) throws RecordNotFoundException;
}