package org.savea.controllers.dtos;

import org.savea.models.Employee;

import java.time.LocalDateTime;

public record EmployeePayload(
        String name,
        LocalDateTime dateOfBirth
) {

    /**
     * Convert to {@link org.savea.models.Employee} object
     */
    public Employee toEmployee() {
        return new Employee(name, dateOfBirth);}
}
