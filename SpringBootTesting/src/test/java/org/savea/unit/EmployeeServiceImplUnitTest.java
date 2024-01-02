package org.savea.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import org.savea.models.Employee;
import org.savea.services.EmployeeRepository;
import org.savea.services.impl.EmployeeServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.lenient;

/**
 * This class is a unit test for the EmployeeServiceImpl class.
 * It uses Mockito to mock the EmployeeRepository, which is a dependency of EmployeeServiceImpl.
 * The @InjectMocks annotation is used to inject the mocked dependencies into the EmployeeServiceImpl.
 * The @Mock annotation is used to create a mock implementation of the EmployeeRepository.
 */
@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplUnitTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    /**
     * This method is annotated with @Before, which means it is executed before each test case.
     * It sets up the mock responses for the methods of EmployeeRepository.
     */
    @BeforeEach
    public void setUp() {
        Employee john = new Employee("john");
        john.setId(11L);

        Employee bob = new Employee("bob");
        Employee alex = new Employee("alex");

        List<Employee> allEmployees = Arrays.asList(john, bob, alex);

        lenient().when(employeeRepository.findByName(john.getName())).thenReturn(john);
        lenient().when(employeeRepository.findByName(alex.getName())).thenReturn(alex);
        lenient().when(employeeRepository.findByName("wrong_name")).thenReturn(null);
        lenient().when(employeeRepository.findById(john.getId())).thenReturn(Optional.of(john));
        lenient().when(employeeRepository.findAll()).thenReturn(allEmployees);
        lenient().when(employeeRepository.findById(-99L)).thenReturn(Optional.empty());
    }

    /**
     * This test case verifies the functionality of the getEmployeeByName method in the EmployeeServiceImpl.
     * The method is expected to return the employee with the given name if such an employee exists.
     */
    @Test
    void whenValidName_thenEmployeeShouldBeFound() {
        String name = "alex";
        Employee found = employeeService.getEmployeeByName(name);

        assertThat(found.getName()).isEqualTo(name);
    }

    /**
     * This test case verifies the functionality of the getEmployeeByName method in the EmployeeServiceImpl when
     * provided with an invalid name.
     * The method is expected to return null if no employee with the given name exists.
     */
    @Test
    void whenInValidName_thenEmployeeShouldNotBeFound() {
        Employee fromDb = employeeService.getEmployeeByName("wrong_name");
        assertThat(fromDb).isNull();

        verifyFindByNameIsCalledOnce("wrong_name");
    }

    /**
     * This test case verifies the functionality of the exists method in the EmployeeServiceImpl.
     * The method is expected to return true if an employee with the given name exists.
     */
    @Test
    void whenValidName_thenEmployeeShouldExist() {
        boolean doesEmployeeExist = employeeService.exists("john");
        assertThat(doesEmployeeExist).isTrue();

        verifyFindByNameIsCalledOnce("john");
    }

    /**
     * This test case verifies the functionality of the exists method in the EmployeeServiceImpl when provided with
     * an invalid name.
     * The method is expected to return false if no employee with the given name exists.
     */
    @Test
    void whenNonExistingName_thenEmployeeShouldNotExist() {
        boolean doesEmployeeExist = employeeService.exists("some_name");
        assertThat(doesEmployeeExist).isFalse();

        verifyFindByNameIsCalledOnce("some_name");
    }

    /**
     * This test case verifies the functionality of the getEmployeeById method in the EmployeeServiceImpl.
     * The method is expected to return the employee with the given ID if such an employee exists.
     */
    @Test
    void whenValidId_thenEmployeeShouldBeFound() {
        Employee fromDb = employeeService.getEmployeeById(11L);
        assertThat(fromDb.getName()).isEqualTo("john");

        verifyFindByIdIsCalledOnce();
    }

    /**
     * This test case verifies the functionality of the getEmployeeById method in the EmployeeServiceImpl when
     * provided with an invalid ID.
     * The method is expected to return null if no employee with the given ID exists.
     */
    @Test
    void whenInValidId_thenEmployeeShouldNotBeFound() {
        Employee fromDb = employeeService.getEmployeeById(-99L);
        verifyFindByIdIsCalledOnce();
        assertThat(fromDb).isNull();
    }

    /**
     * This test case verifies the functionality of the getAllEmployees method in the EmployeeServiceImpl.
     * The method is expected to return all employees.
     */
    @Test
    void given3Employees_whenGetAll_thenReturn3Records() {
        Employee alex = new Employee("alex");
        Employee john = new Employee("john");
        Employee bob = new Employee("bob");

        List<Employee> allEmployees = employeeService.getAllEmployees();
        verifyFindAllEmployeesIsCalledOnce();
        assertThat(allEmployees).hasSize(3)
                .extracting(Employee::getName)
                .contains(alex.getName(), john.getName(), bob.getName());
    }

    /**
     * This helper method is used to verify that the findByName method of the EmployeeRepository is called exactly once.
     * It also resets the mock after the verification.
     *
     * @param name the name of the employee to find
     */
    private void verifyFindByNameIsCalledOnce(String name) {
        Mockito.verify(employeeRepository, VerificationModeFactory.times(1)).findByName(name);
        Mockito.reset(employeeRepository);
    }

    /**
     * This helper method is used to verify that the findById method of the EmployeeRepository is called exactly once.
     * It also resets the mock after the verification.
     */
    private void verifyFindByIdIsCalledOnce() {
        Mockito.verify(employeeRepository, VerificationModeFactory.times(1))
                .findById(Mockito.anyLong());
        Mockito.reset(employeeRepository);
    }

    /**
     * This helper method is used to verify that the findAll method of the EmployeeRepository is called exactly once.
     * It also resets the mock after the verification.
     */
    private void verifyFindAllEmployeesIsCalledOnce() {
        Mockito.verify(employeeRepository, VerificationModeFactory.times(1)).findAll();
        Mockito.reset(employeeRepository);
    }
}
