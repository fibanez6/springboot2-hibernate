package com.fibanez.springboot.repository;

import com.fibanez.springboot.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByFirstName(String name);
}
