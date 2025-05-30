package com.example.backend.service;

import com.example.backend.dto.request.PointOfInterestRequest;
import com.example.backend.entities.content.Content;
import com.example.backend.entities.poi.PointOfInterest;
import com.example.backend.entities.users.User;
import com.example.backend.entities.poi.PointOfInterestType;
import com.example.backend.repository.ContentRepository;
import com.example.backend.repository.PointOfInterestRepository;
import com.example.backend.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private ContentRepository contentRepository;

    public PointOfInterestService() {
    }

    @Transactional
    public PointOfInterest createPOI(PointOfInterestRequest request) {

        if(poiRepository.existsByNameAndLatitudeAndLongitude(request.name(), request.latitude(), request.longitude())) throw new EntityExistsException("Point of Interest already existing!\n" + request);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User author = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User Not Found"));
        PointOfInterest pointOfInterest = PointOfInterest.builder()
                .name(request.name())
                .description(request.description())
                .latitude(request.latitude())
                .longitude(request.longitude())
                .type(request.type())
                .openTime(request.openTime())
                .closeTime(request.closeTime())
                .build()
                .setAuthor(author)
                .setCreatedAt(new Date());

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

    public void deletePOI(Long id) {
        PointOfInterest poi = poiRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Point of Interest not Found!"));
        List<Long> mcIds = poi.getContents().stream()
                .map(Content::getId)
                .toList();
        contentRepository.deleteAllById(mcIds);
        poiRepository.delete(poi);
    }
}
