package com.example.backend.service;

import com.example.backend.entities.PointOfInterest;
import com.example.backend.entities.enums.PointOfInterestType;
import com.example.backend.repository.PointOfInterestRepository;
import com.example.backend.utility.PointOfInterestBuilder;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PointOfInterestService {

    @Autowired
    private PointOfInterestRepository poiRepository;

    public PointOfInterestService() {
    }

    public PointOfInterest createPOI(PointOfInterest pointOfInterest) {
        return this.poiRepository.save(PointOfInterestBuilder.build(pointOfInterest));
    }


    public PointOfInterest updatePOI(int id, PointOfInterest pointOfInterestDetails) {
        PointOfInterest pointOfInterest = poiRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Point of Interest not Found!"));
        pointOfInterest.setName(pointOfInterestDetails.getName());
        return poiRepository.save(pointOfInterest);
    }

    public List<PointOfInterest> searchPOIByName(String name) {
        if(name == null) return List.of();
        return poiRepository.searchByName(name);
    }

    public List<PointOfInterest> searchPOIByDescription(String description) {
        if(description == null) return List.of();
        return poiRepository.searchByDescription(description);
    }

    public List<PointOfInterest> searchPOIByType(PointOfInterestType type) {
        if(type == null) return List.of();
        return poiRepository.searchByType(type);
    }

    public List<PointOfInterest> getAllPOIs() {
        return poiRepository.findAll();
    }

    public PointOfInterest getPOIById(int id) {
        return poiRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Point of Interest not Found!"));
    }

    public void deleteAllPOIs() {
        this.poiRepository.deleteAll();
    }

    public void deletePOI(int id) {
        this.poiRepository.deleteById(id);
    }
}
