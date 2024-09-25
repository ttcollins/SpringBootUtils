package org.savea.hibernatespatial.services;

import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.savea.hibernatespatial.controllers.dtos.PersonLocationDTO;
import org.savea.hibernatespatial.models.PersonLocation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonLocationServiceImpl implements PersonLocationService {

    private final PersonLocationRepo repository;
    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    @Override
    public PersonLocationDTO saveLocation(String name, double latitude, double longitude) {
        Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        PersonLocation personLocation = new PersonLocation();
        personLocation.setName(name);
        personLocation.setLocation(point);
        PersonLocation savedLocation = repository.save(personLocation);
        return convertToDTO(savedLocation);
    }

    @Override
    public List<PersonLocationDTO> findNearby(double latitude, double longitude, double distance) {
        String pointWKT = String.format("POINT(%f %f)", longitude, latitude);
        List<PersonLocation> locations = repository.findNearby(pointWKT, distance);
        return locations.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private PersonLocationDTO convertToDTO(PersonLocation personLocation) {
        PersonLocationDTO dto = new PersonLocationDTO();
        dto.setId(personLocation.getId());
        dto.setName(personLocation.getName());
        dto.setLatitude(personLocation.getLocation().getY());
        dto.setLongitude(personLocation.getLocation().getX());
        return dto;
    }
}
