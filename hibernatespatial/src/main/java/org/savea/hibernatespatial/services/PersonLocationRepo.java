package org.savea.hibernatespatial.services;

import org.savea.hibernatespatial.models.PersonLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonLocationRepo  extends JpaRepository<PersonLocation, Long> {

    @Query(value = "SELECT * FROM person_location p WHERE ST_Distance(p.location, ST_GeomFromText(:point, 4326)) <= :distance", nativeQuery = true)
    List<PersonLocation> findNearby(@Param("point") String point, @Param("distance") double distance);
}
