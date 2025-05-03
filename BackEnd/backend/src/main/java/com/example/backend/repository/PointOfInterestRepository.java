package com.example.backend.repository;

import com.example.backend.entities.poi.PointOfInterest;
import com.example.backend.entities.poi.PointOfInterestType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PointOfInterestRepository extends JpaRepository<PointOfInterest, Integer> {

    Optional<PointOfInterest> findByNameAndLatitudeAndLongitude(String name, double lat, double lon);
    List<PointOfInterest> searchByName(String name);
    List<PointOfInterest> searchByType(PointOfInterestType type);
}
