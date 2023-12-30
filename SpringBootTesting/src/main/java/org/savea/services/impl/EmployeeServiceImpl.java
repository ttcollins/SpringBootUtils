package org.savea.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.savea.models.Employee;
import org.savea.services.EmployeeRepository;
import org.savea.services.EmployeeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public Employee getEmployeeByName(String name) {
        return employeeRepository.findByName(name);
    }

    @Override
    public boolean exists(String name) {
        return employeeRepository.findByName(name) != null;
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public void createDefaultEmployees() {
        log.info("Creating default employees...");

        /*A map containing employee names and their dates of birth in LocalDateTime format*/
        Map<String, LocalDateTime> employees = new HashMap<>();
        employees.put("John Doe", LocalDateTime.of(1990, 1, 1, 0, 0));
        employees.put("Jane Doe", LocalDateTime.of(1991, 1, 1, 0, 0));
        employees.put("John Smith", LocalDateTime.of(1992, 1, 1, 0, 0));

        /*Iterate through the map and create employees*/
        employees.forEach((name, dob) -> {
            if (!exists(name)) {
                Employee employee = new Employee();
                employee.setName(name);
                employee.setDateOfBirth(dob);
                employeeRepository.save(employee);
            }
        });

        log.info("Default employees created");
    }
}
