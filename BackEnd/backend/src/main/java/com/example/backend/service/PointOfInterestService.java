package com.example.backend.service;

import com.example.backend.entities.content.MultimediaContent;
import com.example.backend.entities.poi.PointOfInterest;
import com.example.backend.entities.users.User;
import com.example.backend.entities.poi.PointOfInterestType;
import com.example.backend.repository.MultimediaContentRepository;
import com.example.backend.repository.PointOfInterestRepository;
import com.example.backend.repository.UserRepository;
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
    @Autowired
    private MultimediaContentRepository multimediaContentRepository;

    public PointOfInterestService() {
    }

    public PointOfInterest createPOI(PointOfInterest pointOfInterest, String firstname, String lastname) {

        if(poiRepository.existsByNameAndLatitudeAndLongitude(pointOfInterest.getName(), pointOfInterest.getLatitude(), pointOfInterest.getLongitude()))
            throw new EntityExistsException("Point of Interest already existing!\n" + pointOfInterest);

        Optional<User> optionalAuthor = userRepository.findByFirstnameAndLastname(firstname, lastname);

        if(optionalAuthor.isEmpty())
            throw new EntityNotFoundException("User not found: " + firstname + " " + lastname);

        User author = optionalAuthor.get();
        pointOfInterest.setCreatedAt(new Date());
        pointOfInterest.setAuthor(author);

        return this.poiRepository.save(pointOfInterest);
    }


    public PointOfInterest updatePOI(Long id, PointOfInterest pointOfInterestDetails) {
        Optional<PointOfInterest> optionalPOI = poiRepository.findById(id);
        if(optionalPOI.isPresent()) {
            PointOfInterest pointOfInterest = optionalPOI.get()
                    .setName(pointOfInterestDetails.getName())
                    .setDescription(pointOfInterestDetails.getDescription())
                    .setLatitude(pointOfInterestDetails.getLatitude())
                    .setLongitude(pointOfInterestDetails.getLongitude())
                    .setType(pointOfInterestDetails.getType())
                    .setOpenTime(pointOfInterestDetails.getOpenTime())
                    .setCloseTime(pointOfInterestDetails.getCloseTime())
                    .setUpdatedAt(new Date());
            return poiRepository.save(pointOfInterest);
        } else {
            throw new EntityNotFoundException("Point of Interest not found!");
        }

    }

    public List<PointOfInterest> searchPOI(String name, PointOfInterestType type) {
        return (name != null && !name.isEmpty())? poiRepository.findByNameContainingIgnoreCase(name):
                (type != null)? poiRepository.findByType(type): poiRepository.findAll();
    }

    public List<PointOfInterest> getAllPOIs() {
        return poiRepository.findAll();
    }

    public PointOfInterest getPOIById(Long id) {
        return poiRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Point of Interest not Found!"));
    }

    public void deleteAllPOIs() {
        multimediaContentRepository.deleteAll();
        poiRepository.deleteAll();
    }

    public void deletePOI(Long id) {
        PointOfInterest poi = poiRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Point of Interest not Found!"));
        List<Long> mcIds = poi.getMultimediaContents().stream()
                .map(MultimediaContent::getId)
                .toList();
        multimediaContentRepository.deleteAllById(mcIds);
        poiRepository.delete(poi);
    }
}
