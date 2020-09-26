package com.fibanez.springboot.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.fibanez.springboot.exception.RecordNotFoundException;
import com.fibanez.springboot.model.Employee;
import com.fibanez.springboot.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class EmployeeServiceImplIntegrationTest {

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
        @Bean
        public EmployeeService employeeService() {
            return new EmployeeServiceImpl();
        }
    }

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Before
    public void setUp() {
        Employee john = new Employee("john", "test@test.com");
        john.setId(11L);

        Employee bob = new Employee("bob", "test@test.com");
        Employee alex = new Employee("alex", "test@test.com");

        List<Employee> allEmployees = Arrays.asList(john, bob, alex);

        Mockito.when(employeeRepository.findByFirstName(john.getFirstName())).thenReturn(john);
        Mockito.when(employeeRepository.findByFirstName(alex.getFirstName())).thenReturn(alex);
        Mockito.when(employeeRepository.findByFirstName("wrong_name")).thenReturn(null);
        Mockito.when(employeeRepository.findById(john.getId())).thenReturn(Optional.of(john));
        Mockito.when(employeeRepository.findAll()).thenReturn(allEmployees);
        Mockito.when(employeeRepository.findById(-99L)).thenReturn(Optional.empty());
    }

    @Test
    public void whenValidName_thenEmployeeShouldBeFound() throws RecordNotFoundException {
        String name = "alex";
        Employee found = employeeService.getEmployeeByFirstName(name);

        assertThat(found.getFirstName()).isEqualTo(name);
    }

    @Test(expected = RecordNotFoundException.class)
    public void whenInValidName_thenEmployeeShouldNotBeFound() throws RecordNotFoundException {
        Employee fromDb = employeeService.getEmployeeByFirstName("wrong_name");
    }

    @Test
    public void whenValidId_thenEmployeeShouldBeFound() throws RecordNotFoundException {
        Employee fromDb = employeeService.getEmployeeById(11L);
        assertThat(fromDb.getFirstName()).isEqualTo("john");

        verifyFindByIdIsCalledOnce();
    }

    @Test(expected = RecordNotFoundException.class)
    public void whenInValidId_thenEmployeeShouldNotBeFound() throws RecordNotFoundException {
        Employee fromDb = employeeService.getEmployeeById(-99L);
        verifyFindByIdIsCalledOnce();
        assertThat(fromDb).isNull();
    }

    @Test
    public void given3Employees_whengetAll_thenReturn3Records() {
        Employee alex = new Employee("alex", "test@test.com");
        Employee john = new Employee("john", "test@test.com");
        Employee bob = new Employee("bob", "test@test.com");

        List<Employee> allEmployees = employeeService.getAllEmployees();
        verifyFindAllEmployeesIsCalledOnce();
        assertThat(allEmployees).hasSize(3).extracting(Employee::getFirstName).contains(alex.getFirstName(), john.getFirstName(), bob.getFirstName());
    }

    private void verifyFindByNameIsCalledOnce(String name) {
        Mockito.verify(employeeRepository, VerificationModeFactory.times(1)).findByFirstName(name);
        Mockito.reset(employeeRepository);
    }

    private void verifyFindByIdIsCalledOnce() {
        Mockito.verify(employeeRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
        Mockito.reset(employeeRepository);
    }

    private void verifyFindAllEmployeesIsCalledOnce() {
        Mockito.verify(employeeRepository, VerificationModeFactory.times(1)).findAll();
        Mockito.reset(employeeRepository);
    }
}