package org.savea.hibernatespatial.models;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "person_location")
@Setter
@NoArgsConstructor
public class PersonLocation {
    private Long id;
    private String name;
    private Point location;

    public PersonLocation(String name, Point location) {
        this.name = name;
        this.location = location;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "location", columnDefinition = "Geometry")
    public Point getLocation() {
        return location;
    }
}
