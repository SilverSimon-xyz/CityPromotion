package com.example.backend.repository;

import com.example.backend.entities.poi.PointOfInterest;
import com.example.backend.entities.poi.PointOfInterestType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointOfInterestRepository extends JpaRepository<PointOfInterest, Long> {

    boolean existsByNameAndLatitudeAndLongitude(String name, double lat, double lon);
    List<PointOfInterest> findByNameContainingIgnoreCase(String name);
    List<PointOfInterest> findByType(PointOfInterestType type);
}
