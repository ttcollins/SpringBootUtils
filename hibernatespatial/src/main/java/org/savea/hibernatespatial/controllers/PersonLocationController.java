package org.savea.hibernatespatial.controllers;

import lombok.RequiredArgsConstructor;
import org.savea.hibernatespatial.controllers.dtos.PersonLocationDTO;
import org.savea.hibernatespatial.models.PersonLocation;
import org.savea.hibernatespatial.services.PersonLocationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class PersonLocationController {

    private final PersonLocationService service;

    @PostMapping
    public PersonLocationDTO saveLocation(@RequestParam String name, @RequestParam double latitude, @RequestParam double longitude) {
        return service.saveLocation(name, latitude, longitude);
    }

    @GetMapping("/nearby")
    public List<PersonLocationDTO> findNearby(@RequestParam double latitude, @RequestParam double longitude, @RequestParam double distance) {
        return service.findNearby(latitude, longitude, distance);
    }
}