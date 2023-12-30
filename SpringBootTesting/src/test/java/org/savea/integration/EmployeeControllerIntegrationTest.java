package org.savea.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.savea.controllers.endpoints.EmployeeRestController;
import org.savea.models.Employee;
import org.savea.services.EmployeeService;
import org.savea.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeRestController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService service;

    /**
     * This test method verifies the creation of an Employee through a POST request.
     *
     * @throws Exception if any processing error occurs
     */
    @Test
    public void employeeCreation() throws Exception {
        // Create a new Employee object named "alex"
        Employee alex = new Employee("alex");

        // Mock the service's save method to return the created Employee when called
        given(service.save(Mockito.any())).willReturn(alex);

        // Perform a POST request to the "/api/employees" endpoint
        // The content of the request is the JSON representation of the created Employee
        mvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(alex)))
                // Expect the HTTP status to be 201 (Created)
                .andExpect(status().isCreated())
                // Expect the name of the returned Employee in the response body to be "alex"
                .andExpect(jsonPath("$.name", is("alex")));

        // Verify that the service's save method was called exactly once
        verify(service, VerificationModeFactory.times(1))
                .save(Mockito.any());

        // Reset the mock service for the next test
        reset(service);
    }

    /**
     * This test method verifies the retrieval of all Employees through a GET request.
     *
     * @throws Exception if any processing error occurs
     */
    @Test
    public void employeeRetrieval() throws Exception {
        // Create new Employee objects named "alex", "john", and "bob"
        Employee alex = new Employee("alex");
        Employee john = new Employee("john");
        Employee bob = new Employee("bob");

        // Add the created Employees to a List
        List<Employee> allEmployees = Arrays.asList(alex, john, bob);

        // Mock the service's getAllEmployees method to return the list of all Employees when called
        given(service.getAllEmployees()).willReturn(allEmployees);

        // Perform a GET request to the "/api/employees" endpoint
        mvc.perform(get("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                // Expect the HTTP status to be 200 (OK)
                .andExpect(status().isOk())
                // Expect the size of the returned JSON array to be 3
                .andExpect(jsonPath("$", hasSize(3)))
                // Expect the names of the returned Employees in the response body to match the names of the created Employees
                .andExpect(jsonPath("$[0].name", is(alex.getName())))
                .andExpect(jsonPath("$[1].name", is(john.getName())))
                .andExpect(jsonPath("$[2].name", is(bob.getName())));

        // Verify that the service's getAllEmployees method was called exactly once
        verify(service, VerificationModeFactory.times(1))
                .getAllEmployees();

        // Reset the mock service for the next test
        reset(service);
    }

}
