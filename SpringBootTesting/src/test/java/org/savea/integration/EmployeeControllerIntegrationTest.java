package org.savea.integration;

import org.junit.jupiter.api.AfterEach;
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
 * This class is annotated with @RunWith(SpringRunner.class), which means it uses Spring's testing support.
 * SpringRunner is a custom extension of JUnit's BlockJUnit4ClassRunner which provides functionality of the Spring TestContext Framework to our tests.
 *
 * @AutoConfigureMockMvc is used to auto-configure MockMvc which offers a powerful way to easily test MVC controllers without needing to start a full HTTP server.
 *
 * @EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class) is used to enable auto-configuration of the Spring Application Context, attempting to guess and configure beans that you are likely to need.
 * The SecurityAutoConfiguration class is excluded from the auto-configuration as we don't want to apply Spring Security's default configuration in our tests.
 *
 * @AutoConfigureTestDatabase is used to configure a test database that replaces any application-defined DataSource.
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@AutoConfigureTestDatabase
class EmployeeControllerIntegrationTest {

    // The MockMvc instance is injected here. This is the main entry point for server-side Spring MVC test support.
    @Autowired
    private MockMvc mvc;

    // The EmployeeRepository instance is injected here. This is used to interact with the database.
    @Autowired
    private EmployeeRepository repository;

    /**
     * This method is annotated with @Before, which means it is executed before each test case.
     * It is used to reset the database by deleting all employees.
     */
    @BeforeEach
    public void setUp() {
        repository.deleteAll();
    }

    /**
     * This method is annotated with @After, which means it is executed after each test case.
     * It is used to reset the database by deleting all employees.
     */
    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }

    /**
     * This test case verifies the functionality of the POST /api/employees endpoint.
     * The endpoint is expected to create a new employee when provided with valid input.
     * <p>
     * The test case follows these steps:
     * 1. An employee named "bob" is created.
     * 2. A POST request is performed to the /api/employees endpoint with the new employee as the body.
     * 3. The `findAll` method is called on the `repository` to retrieve all employees.
     * 4. An assertion is made to ensure that the name of the employee in the database matches the name of the
     * created employee.
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
     * The endpoint is expected to return all employees in the database.
     * <p>
     * The test case follows these steps:
     * 1. Two employees, "bob" and "alex", are created using the `createTestEmployee` method.
     * 2. A GET request is performed to the /api/employees endpoint.
     * 3. Assertions are made to ensure that the status is 200, the content type is JSON, and the names of the
     * employees in the response match the names of the created employees.
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
     * The employee is persisted in the database using the `saveAndFlush` method on the `repository`.
     *
     * @param name the name of the employee to create
     */
    private void createTestEmployee(String name) {
        Employee emp = new Employee(name);
        repository.saveAndFlush(emp);
    }
}
