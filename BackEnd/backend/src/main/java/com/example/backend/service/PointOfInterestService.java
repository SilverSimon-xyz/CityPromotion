package com.example.backend.service;

import com.example.backend.entities.poi.PointOfInterest;
import com.example.backend.entities.users.User;
import com.example.backend.entities.poi.PointOfInterestType;
import com.example.backend.repository.PointOfInterestRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.utility.PointOfInterestBuilder;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PointOfInterestService {

    @Autowired
    private PointOfInterestRepository poiRepository;
    @Autowired
    private UserRepository userRepository;

    public PointOfInterestService() {
    }

    public PointOfInterest createPOI(PointOfInterest pointOfInterest, String authorName) {
        User author = userRepository.findByName(authorName).orElseThrow(() -> new EntityNotFoundException("User not found!"));
        Optional<PointOfInterest> optional = poiRepository.findByNameAndLatitudeAndLongitude(
                pointOfInterest.getName(), pointOfInterest.getLatitude(), pointOfInterest.getLongitude()
        );
        if(optional.isPresent()) {
            throw new EntityExistsException("Point of Interest already existing!");
        }
        pointOfInterest.setAuthor(author);
        PointOfInterest newPOI = PointOfInterestBuilder.build(pointOfInterest);
        return this.poiRepository.save(newPOI);
    }


    public PointOfInterest updatePOI(int id, PointOfInterest pointOfInterestDetails) {
        PointOfInterest pointOfInterest = poiRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Point of Interest not Found!"));
        pointOfInterest.setName(pointOfInterestDetails.getName());
        pointOfInterest.setDescription(pointOfInterestDetails.getDescription());
        pointOfInterest.setLatitude(pointOfInterestDetails.getLatitude());
        pointOfInterest.setLongitude(pointOfInterestDetails.getLongitude());
        pointOfInterest.setType(pointOfInterestDetails.getType());
        pointOfInterest.setOpenTime(pointOfInterestDetails.getOpenTime());
        pointOfInterest.setCloseTime(pointOfInterestDetails.getCloseTime());
        pointOfInterest.setUpdatedAt(new Date());
        return poiRepository.save(pointOfInterest);
    }

    public List<PointOfInterest> searchPOIByName(String name) {
        if(name == null) return List.of();
        return poiRepository.searchByName(name);
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
