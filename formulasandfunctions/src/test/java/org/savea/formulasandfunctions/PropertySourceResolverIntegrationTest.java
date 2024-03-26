package org.savea.formulasandfunctions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This is a Spring Boot test class for testing the properties' resolution.
 * It uses the Environment interface provided by Spring to resolve properties.
 */
@SpringBootTest
class PropertySourceResolverIntegrationTest {

    /**
     * The Environment interface is used to get the properties from the application's environment.
     * It is automatically managed and injected by Spring.
     */
    @Autowired
    private Environment env;

    /**
     * This test method checks if the property 'spring.datasource.hikari.jdbc-url' is correctly resolved.
     * It asserts that the resolved property value is equal to 'jdbc:h2:mem:test_formulas_and_functions_db'.
     */
    @Test
    void shouldTestResourceFile_overridePropertyValues() {
        // Get the property value using the getProperty method of the Environment interface
        String dbSetUp = env.getProperty("spring.datasource.hikari.jdbc-url");

        // Assert that the resolved property value is equal to 'jdbc:h2:mem:test_formulas_and_functions_db'
        assertEquals("jdbc:h2:mem:test_formulas_and_functions_db", dbSetUp);
    }
}
