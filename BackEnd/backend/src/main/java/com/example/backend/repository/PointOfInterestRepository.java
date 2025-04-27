package com.example.backend.repository;

import com.example.backend.entities.PointOfInterest;
import com.example.backend.entities.enums.PointOfInterestType;
import com.example.backend.entities.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PointOfInterestRepository extends JpaRepository<PointOfInterest, Integer> {

    Optional<PointOfInterest> findByNameAndLatitudeAndLongitude(String name, double lat, double lon);
    List<PointOfInterest> searchByName(String name);
    List<PointOfInterest> searchByDescription(String description);
    List<PointOfInterest> searchByStatus(Status status);
    List<PointOfInterest> searchByType(PointOfInterestType type);
}
