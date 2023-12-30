package org.savea.services.seeders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.savea.services.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final EmployeeService employeeService;

    @Override
    public void run(String... args) {
        log.info("Seeding data...");
        employeeService.createDefaultEmployees();
    }
}
