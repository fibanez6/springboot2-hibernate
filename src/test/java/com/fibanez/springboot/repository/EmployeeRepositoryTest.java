package com.fibanez.springboot.repository;

import com.fibanez.springboot.model.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void whenFindByFirstName_thenReturnEmployee() {
        // given
        Employee alex = new Employee("alex", "test€test.com");
        entityManager.persist(alex);
        entityManager.flush();

        // when
        Employee found = employeeRepository.findByFirstName(alex.getFirstName());

        // then
        assertThat(found.getFirstName()).isEqualTo(alex.getFirstName());
    }

    @Test
    public void whenInvalidName_thenReturnNull() {
        Employee fromDb = employeeRepository.findByFirstName("doesNotExist");
        assertThat(fromDb).isNull();
    }

    @Test
    public void whenFindById_thenReturnEmployee() {
        Employee emp = new Employee("test", "test€test.com");
        entityManager.persistAndFlush(emp);

        Employee fromDb = employeeRepository.findById(emp.getId()).orElse(null);
        assertThat(fromDb.getFirstName()).isEqualTo(emp.getFirstName());
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Employee fromDb = employeeRepository.findById(-11l).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfEmployees_whenFindAll_thenReturnAllEmployees() {
        Employee alex = new Employee("alex", "test€test.com");
        Employee ron = new Employee("ron", "test€test.com");
        Employee bob = new Employee("bob", "test€test.com");

        entityManager.persist(alex);
        entityManager.persist(bob);
        entityManager.persist(ron);
        entityManager.flush();

        List<Employee> allEmployees = employeeRepository.findAll();

        assertThat(allEmployees).hasSize(3).extracting(Employee::getFirstName).containsOnly(alex.getFirstName(), ron.getFirstName(), bob.getFirstName());
    }
}