package org.savea.hibernatespatial.controllers.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonLocationDTO {
    private Long id;
    private String name;
    private double latitude;
    private double longitude;
}
