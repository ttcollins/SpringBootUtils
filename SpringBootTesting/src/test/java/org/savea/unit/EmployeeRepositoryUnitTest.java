package org.savea.unit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.savea.models.Employee;
import org.savea.services.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * This test case verifies the functionality of the `findByName` method in the `EmployeeRepository`.
     * The method is expected to return the employee with the given name if such an employee exists in the database.
     * <p>
     * The test case follows these steps:
     * 1. An employee named "alex" is created and persisted in the database.
     * 2. The `findByName` method is called on the `employeeRepository` with the name of the persisted employee.
     * 3. An assertion is made to ensure that the name of the returned employee matches the name of the persisted employee.
     */
    @Test
    public void whenFindByName_thenReturnEmployee() {
        Employee alex = new Employee("alex");
        entityManager.persistAndFlush(alex);

        Employee found = employeeRepository.findByName(alex.getName());
        assertThat(found.getName()).isEqualTo(alex.getName());
    }

    /**
     * This test case verifies the functionality of the `findByName` method in the `EmployeeRepository` when
     * provided with an invalid name.
     * The method is expected to return null if no employee with the given name exists in the database.
     * <p>
     * The test case follows these steps:
     * 1. The `findByName` method is called on the `employeeRepository` with an invalid name ("doesNotExist").
     * 2. An assertion is made to ensure that the returned employee is null.
     */
    @Test
    public void whenInvalidName_thenReturnNull() {
        Employee fromDb = employeeRepository.findByName("doesNotExist");
        assertThat(fromDb).isNull();
    }

    /**
     * This test case verifies the functionality of the `findById` method in the `EmployeeRepository`.
     * The method is expected to return the employee with the given ID if such an employee exists in the database.
     * <p>
     * The test case follows these steps:
     * 1. An employee named "test" is created and persisted in the database.
     * 2. The `findById` method is called on the `employeeRepository` with the ID of the persisted employee.
     * 3. The `orElse` method is used to return null if the `Optional` returned by `findById` is empty.
     * 4. An assertion is made to ensure that the returned employee is not null and that its name matches the name
     * of the persisted employee.
     */
    @Test
    public void whenFindById_thenReturnEmployee() {
        Employee emp = new Employee("test");
        entityManager.persistAndFlush(emp);

        Employee fromDb = employeeRepository.findById(emp.getId()).orElse(null);
        assert fromDb != null;
        assertThat(fromDb.getName()).isEqualTo(emp.getName());
    }

    /**
     * This test case verifies the functionality of the `findById` method in the `EmployeeRepository` when provided
     * with an invalid ID.
     * The method is expected to return null if no employee with the given ID exists in the database.
     * <p>
     * The test case follows these steps:
     * 1. The `findById` method is called on the `employeeRepository` with an invalid ID (-11L).
     * 2. The `orElse` method is used to return null if the `Optional` returned by `findById` is empty.
     * 3. An assertion is made to ensure that the returned employee is null.
     */
    @Test
    public void whenInvalidId_thenReturnNull() {
        Employee fromDb = employeeRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    /**
     * This test case verifies the functionality of the `findAll` method in the `EmployeeRepository`.
     * The method is expected to return all employees present in the database.
     * <p>
     * The test case follows these steps:
     * 1. Three employees, Alex, Ron, and Bob, are created and persisted in the database.
     * 2. The `findAll` method is called on the `employeeRepository` to retrieve all employees.
     * 3. Assertions are made to ensure that the size of the returned list is 3 (as three employees were persisted)
     * and that the names of the employees in the returned list match the names of the persisted employees.
     */
    @Test
    public void givenSetOfEmployees_whenFindAll_thenReturnAllEmployees() {
        Employee alex = new Employee("alex");
        Employee ron = new Employee("ron");
        Employee bob = new Employee("bob");

        entityManager.persist(alex);
        entityManager.persist(bob);
        entityManager.persist(ron);
        entityManager.flush();

        List<Employee> allEmployees = employeeRepository.findAll();

        assertThat(allEmployees)
                .hasSize(3)
                .extracting(Employee::getName)
                .containsOnly(alex.getName(), ron.getName(), bob.getName());
    }
}
