package com.example.backend.repository;

import com.example.backend.entities.PointOfInterest;
import com.example.backend.entities.enums.PointOfInterestType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointOfInterestRepository extends JpaRepository<PointOfInterest, Integer> {

    //Optional<PointOfInterest> findByNameAndLatitudeAndLongitude(String name, double lat, double lon);
    List<PointOfInterest> searchByName(String name);
    List<PointOfInterest> searchByDescription(String description);
    List<PointOfInterest> searchByType(PointOfInterestType type);
}
