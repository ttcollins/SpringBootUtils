package org.savea.services;

import org.savea.models.Employee;

import java.util.List;

public interface EmployeeService {

    /**
     * Get employee by id
     * @param id, employee id
     * @return employee
     */
    Employee getEmployeeById(Long id);

    /**
     * Get employee by name
     * @param name, employee name
     * @return employee
     */
    Employee getEmployeeByName(String name);

    /**
     * Get all employees
     * @return list of employees
     */
    List<Employee> getAllEmployees();

    /**
     * Check if employee exists by email
     * @param email, employee email
     * @return true if exists, false otherwise
     */
    boolean exists(String email);

    /**
     * Save employee
     * @param employee, employee to save
     * @return saved employee
     */
    Employee save(Employee employee);

    /**
     * Create default employees
     */
    void createDefaultEmployees();
}
