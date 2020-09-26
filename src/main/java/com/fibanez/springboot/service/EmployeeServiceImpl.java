package com.fibanez.springboot.service;

import com.fibanez.springboot.exception.RecordNotFoundException;
import com.fibanez.springboot.model.Employee;
import com.fibanez.springboot.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
     
    @Autowired
    EmployeeRepository repository;
     
    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = repository.findAll();
         
        if(employeeList.size() > 0) {
            return employeeList;
        } else {
            return new ArrayList<Employee>();
        }
    }
     
    public Employee getEmployeeById(Long id) throws RecordNotFoundException {
        Optional<Employee> employee = repository.findById(id);
         
        if(employee.isPresent()) {
            return employee.get();
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }

    public Employee getEmployeeByFirstName(String firstName) throws RecordNotFoundException {
        Employee employee = repository.findByFirstName(firstName);

        if(Objects.nonNull(employee)) {
            return employee;
        } else {
            throw new RecordNotFoundException("No employee record exist for given firstName");
        }
    }
     
    public Employee createOrUpdateEmployee(Employee entity) throws RecordNotFoundException {
        Optional<Employee> employee = repository.findById(entity.getId());
         
        if (employee.isPresent()) {
            Employee newEntity = employee.get();
            newEntity.setEmail(entity.getEmail());
            newEntity.setFirstName(entity.getFirstName());
            newEntity.setLastName(entity.getLastName());
 
            newEntity = repository.save(newEntity);
             
            return newEntity;
        } else {
            entity = repository.save(entity);
             
            return entity;
        }
    }
     
    public void deleteEmployeeById(Long id) throws RecordNotFoundException {
        Optional<Employee> employee = repository.findById(id);
         
        if(employee.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }
}