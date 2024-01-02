package org.savea.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.savea.Application;
import org.savea.models.Employee;
import org.savea.services.EmployeeRepository;
import org.savea.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * This class is a Spring Boot integration test for the EmployeeController class.
 * It uses the @SpringBootTest annotation to indicate that it should start an embedded servlet container.
 * The @AutoConfigureMockMvc annotation is used to auto-configure a MockMvc instance, which is used to perform HTTP requests.
 * The @EnableAutoConfiguration annotation is used to enable auto-configuration of the Spring Application Context, excluding the SecurityAutoConfiguration class.
 * The @AutoConfigureTestDatabase annotation is used to replace the application-defined DataSource with a test database.
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@AutoConfigureTestDatabase
class EmployeeControllerIntegrationTest {

    /**
     * The MockMvc instance is used to perform HTTP requests in the tests.
     */
    @Autowired
    private MockMvc mvc;

    /**
     * The EmployeeRepository instance is used to interact with the database in the tests.
     */
    @Autowired
    private EmployeeRepository repository;

    /**
     * This method is run before each test case.
     * It uses the deleteAll method of the repository to clear all existing employees from the database.
     * This ensures that each test case starts with a clean database.
     */
    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    /**
     * This test case verifies the functionality of the POST /api/employees endpoint.
     * It creates a new employee and performs a POST request to the endpoint with the new employee as the body.
     * It then retrieves all employees from the database and asserts that the name of the employee in the database matches the name of the created employee.
     */
    @Test
    void whenValidInput_thenCreateEmployee() throws IOException, Exception {
        Employee bob = new Employee("bob");
        mvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(bob)));

        List<Employee> found = repository.findAll();
        assertThat(found).extracting(Employee::getName).containsOnly("bob");
    }

    /**
     * This test case verifies the functionality of the GET /api/employees endpoint.
     * It creates two employees and performs a GET request to the endpoint.
     * It then asserts that the status is 200, the content type is JSON, and the names of the employees in the response match the names of the created employees.
     */
    @Test
    void givenEmployees_whenGetEmployees_thenStatus200() throws Exception {
        createTestEmployee("bob");
        createTestEmployee("alex");

        mvc.perform(get("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[0].name", is("bob")))
                .andExpect(jsonPath("$[1].name", is("alex")));
    }

    /**
     * This helper method is used to create a test employee with the given name.
     * The employee is persisted in the database using the saveAndFlush method of the repository.
     *
     * @param name the name of the employee to create
     */
    private void createTestEmployee(String name) {
        Employee emp = new Employee(name);
        repository.saveAndFlush(emp);
    }
}