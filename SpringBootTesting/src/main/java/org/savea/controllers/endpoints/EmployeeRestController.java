package org.savea.controllers.endpoints;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.savea.controllers.dtos.EmployeeDto;
import org.savea.controllers.dtos.EmployeePayload;
import org.savea.models.Employee;
import org.savea.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeRestController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Object> createEmployee(@RequestBody @NonNull EmployeePayload employee) {
        Employee saved = employeeService.save(employee.toEmployee());
        return new ResponseEntity<>(new EmployeeDto(saved), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Object> getAllEmployees() {
        return new ResponseEntity<>(EmployeeDto.toList(employeeService.getAllEmployees()), HttpStatus.OK);
    }

}
