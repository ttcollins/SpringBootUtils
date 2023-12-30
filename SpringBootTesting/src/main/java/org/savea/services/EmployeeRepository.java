package org.savea.services;

import org.savea.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Find employee by name
     * @param name, employee name
     * @return employee
     */
    Employee findByName(String name);

    /**
     * Find all employees
     * @return list of employees
     */
    List<Employee> findAll();
}
