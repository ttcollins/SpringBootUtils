package org.savea.hibernatespatial.controllers;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.savea.hibernatespatial.models.PersonLocation;
import org.savea.hibernatespatial.services.PersonLocationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class PersonLocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonLocationRepo repository;

    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    @BeforeEach
    public void setup() {
        repository.deleteAll();
        Point point1 = geometryFactory.createPoint(new Coordinate(10.0, 10.0));
        Point point2 = geometryFactory.createPoint(new Coordinate(20.0, 20.0));
        repository.saveAll(List.of(
                new PersonLocation("Alice", point1),
                new PersonLocation("Bob", point2)
        ));
    }

    @Test
    void testSaveLocation() throws Exception {
        mockMvc.perform(post("/locations")
                        .param("name", "Charlie")
                        .param("latitude", "30.0")
                        .param("longitude", "30.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Charlie"))
                .andExpect(jsonPath("$.latitude").value(30.0))
                .andExpect(jsonPath("$.longitude").value(30.0));
    }

    @Test
    void testFindNearby() throws Exception {
        mockMvc.perform(get("/locations/nearby")
                        .param("latitude", "10.0")
                        .param("longitude", "9.0")
                        .param("distance", "1000000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Alice"));
    }
}