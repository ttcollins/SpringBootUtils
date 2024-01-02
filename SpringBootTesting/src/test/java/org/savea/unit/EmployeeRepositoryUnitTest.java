package org.savea.unit;

import org.junit.jupiter.api.Test;
import org.savea.models.Employee;
import org.savea.services.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class is a unit test for the EmployeeRepository class. It uses the @DataJpaTest annotation to set up a test
 * environment with an embedded database and a TestEntityManager for handling database operations.
 * The tests in this class cover the findByName, findById, and findAll methods of the EmployeeRepository.
 */
@DataJpaTest
class EmployeeRepositoryUnitTest {

    /**
     * The EmployeeRepository is the class being tested. It is automatically injected by Spring.
     */
    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * This test checks the findByName method of the EmployeeRepository. It creates and persists an employee,
     * then checks that the findByName method correctly retrieves this employee.
     */
    @Test
    void whenFindByName_thenReturnEmployee() {
        // Create and persist an employee
        Employee alex = new Employee("alex");
        employeeRepository.save(alex);

        // Use the findByName method to retrieve the employee
        Employee found = employeeRepository.findByName(alex.getName());

        // Check that the retrieved employee has the correct name
        assertThat(found.getName()).isEqualTo(alex.getName());
    }

    /**
     * This test checks the findByName method of the EmployeeRepository with an invalid name. It checks that the
     * method correctly returns null when no employee with the given name exists.
     */
    @Test
    void whenInvalidName_thenReturnNull() {
        // Use the findByName method with an invalid name
        Employee fromDb = employeeRepository.findByName("doesNotExist");

        // Check that the method returned null
        assertThat(fromDb).isNull();
    }

    /**
     * This test checks the findById method of the EmployeeRepository. It creates and persists an employee,
     * then checks that the findById method correctly retrieves this employee.
     */
    @Test
    void whenFindById_thenReturnEmployee() {
        // Create and persist an employee
        Employee emp = new Employee("test");
        employeeRepository.save(emp);

        // Use the findById method to retrieve the employee
        Employee fromDb = employeeRepository.findById(emp.getId()).orElse(null);

        // Check that the retrieved employee is not null and has the correct name
        assert fromDb != null;
        assertThat(fromDb.getName()).isEqualTo(emp.getName());
    }

    /**
     * This test checks the findById method of the EmployeeRepository with an invalid ID. It checks that the
     * method correctly returns null when no employee with the given ID exists.
     */
    @Test
    void whenInvalidId_thenReturnNull() {
        // Use the findById method with an invalid ID
        Employee fromDb = employeeRepository.findById(-11L).orElse(null);

        // Check that the method returned null
        assertThat(fromDb).isNull();
    }

    /**
     * This test checks the findAll method of the EmployeeRepository. It creates and persists several employees,
     * then checks that the findAll method correctly retrieves all of these employees.
     */
    @Test
    void givenSetOfEmployees_whenFindAll_thenReturnAllEmployees() {
        // Create and persist several employees
        Employee alex = new Employee("alex");
        Employee ron = new Employee("ron");
        Employee bob = new Employee("bob");

        employeeRepository.save(alex);
        employeeRepository.save(bob);
        employeeRepository.save(ron);

        // Use the findAll method to retrieve all employees
        List<Employee> allEmployees = employeeRepository.findAll();

        // Check that the correct number of employees was retrieved and that their names are correct
        assertThat(allEmployees)
                .hasSize(3)
                .extracting(Employee::getName)
                .containsOnly(alex.getName(), ron.getName(), bob.getName());
    }
}