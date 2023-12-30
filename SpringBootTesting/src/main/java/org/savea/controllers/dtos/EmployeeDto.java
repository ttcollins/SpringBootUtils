package org.savea.controllers.dtos;

import lombok.Getter;
import lombok.Setter;
import org.savea.models.Employee;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class EmployeeDto {

    private Long id;
    private String name;
    private LocalDateTime dob;

    public EmployeeDto(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.dob = employee.getDateOfBirth();
    }

    public static List<EmployeeDto> toList(List<Employee> employees) {
        return employees.stream().map(EmployeeDto::new).toList();
    }

}
