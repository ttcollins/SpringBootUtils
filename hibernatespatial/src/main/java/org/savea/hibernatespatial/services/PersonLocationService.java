package org.savea.hibernatespatial.services;

import org.savea.hibernatespatial.controllers.dtos.PersonLocationDTO;

import java.util.List;

public interface PersonLocationService {

    PersonLocationDTO saveLocation(String name, double latitude, double longitude);

    List<PersonLocationDTO> findNearby(double latitude, double longitude, double distance);
}
