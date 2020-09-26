package com.fibanez.springboot.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fibanez.springboot.JsonUtil;
import com.fibanez.springboot.model.Employee;
import com.fibanez.springboot.service.EmployeeService;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService service;

    @Test
    public void whenPostEmployee_thenCreateEmployee() throws Exception {
        Employee alex = new Employee("alex", "test€test.com");
        given(service.createOrUpdateEmployee(Mockito.any())).willReturn(alex);

        mvc.perform(post("/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJson(alex)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName", is("alex")));
        verify(service, VerificationModeFactory.times(1)).createOrUpdateEmployee(Mockito.any());
        reset(service);
    }

    @Test
    public void givenEmployees_whenGetEmployees_thenReturnJsonArray() throws Exception {
        Employee alex = new Employee("alex", "test€test.com");
        Employee john = new Employee("john", "test€test.com");
        Employee bob = new Employee("bob", "test€test.com");

        List<Employee> allEmployees = Arrays.asList(alex, john, bob);

        given(service.getAllEmployees()).willReturn(allEmployees);

        mvc.perform(get("/employees")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].firstName", is(alex.getFirstName())))
            .andExpect(jsonPath("$[1].firstName", is(john.getFirstName())))
            .andExpect(jsonPath("$[2].firstName", is(bob.getFirstName())));
        verify(service, VerificationModeFactory.times(1)).getAllEmployees();
        reset(service);
    }
}