package com.example.backend.controller;

import com.example.backend.dto.response.PointOfInterestResponse;
import com.example.backend.dto.request.PointOfInterestRequest;
import com.example.backend.entities.poi.PointOfInterest;
import com.example.backend.entities.poi.PointOfInterestType;
import com.example.backend.service.PointOfInterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/poi")
public class PointOfInterestController {

    @Autowired
    private PointOfInterestService pointOfInterestService;

    @GetMapping("/all")
    public ResponseEntity<List<PointOfInterestResponse>> getAllPOIs() {
        List<PointOfInterestResponse> pointOfInterestResponseList = pointOfInterestService.getAllPOIs()
                .stream()
                .map(pointOfInterest -> PointOfInterestResponse
                            .builder()
                                .name(pointOfInterest.getName())
                                .description(pointOfInterest.getDescription())
                                .author(pointOfInterest.getAuthor().getName())
                                .latitude(pointOfInterest.getLatitude())
                                .longitude(pointOfInterest.getLongitude())
                                .type(pointOfInterest.getType())
                                .openTime(pointOfInterest.getOpenTime())
                                .closeTime(pointOfInterest.getOpenTime())
                                .createdAt(pointOfInterest.getCreatedAt())
                                .updatedAt(pointOfInterest.getCreatedAt())
                        .build()
                )
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(pointOfInterestResponseList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<PointOfInterestResponse> getPOIDetailsById(@PathVariable int id) {
        PointOfInterest pointOfInterest = pointOfInterestService.getPOIById(id);
        PointOfInterestResponse response = PointOfInterestResponse
                .builder()
                .name(pointOfInterest.getName())
                .description(pointOfInterest.getDescription())
                .author(pointOfInterest.getAuthor().getName())
                .latitude(pointOfInterest.getLatitude())
                .longitude(pointOfInterest.getLongitude())
                .type(pointOfInterest.getType())
                .openTime(pointOfInterest.getOpenTime())
                .closeTime(pointOfInterest.getOpenTime())
                .createdAt(pointOfInterest.getCreatedAt())
                .updatedAt(pointOfInterest.getCreatedAt())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/find/name")
    public ResponseEntity<List<PointOfInterestResponse>> getPOIDetailsByName(@RequestParam String name) {
        List<PointOfInterest> pointOfInterestList = pointOfInterestService.searchPOIByName(name);
        List<PointOfInterestResponse> pointOfInterestResponseList = pointOfInterestList
                        .stream()
                        .map(pointOfInterest -> PointOfInterestResponse
                                .builder()
                                .name(pointOfInterest.getName())
                                .description(pointOfInterest.getDescription())
                                .author(pointOfInterest.getAuthor().getName())
                                .latitude(pointOfInterest.getLatitude())
                                .longitude(pointOfInterest.getLongitude())
                                .type(pointOfInterest.getType())
                                .openTime(pointOfInterest.getOpenTime())
                                .closeTime(pointOfInterest.getOpenTime())
                                .createdAt(pointOfInterest.getCreatedAt())
                                .updatedAt(pointOfInterest.getCreatedAt())
                                .build())
                        .toList();
        return ResponseEntity.status(HttpStatus.OK).body(pointOfInterestResponseList);
    }

    @GetMapping("/find/type")
    public ResponseEntity<List<PointOfInterestResponse>> getPOIDetailsByType(@RequestParam PointOfInterestType type) {
        List<PointOfInterest> pointOfInterestList = pointOfInterestService.searchPOIByType(type);
        List<PointOfInterestResponse> pointOfInterestResponseList = pointOfInterestList
                .stream()
                .map(pointOfInterest -> PointOfInterestResponse
                        .builder()
                        .name(pointOfInterest.getName())
                        .description(pointOfInterest.getDescription())
                        .author(pointOfInterest.getAuthor().getName())
                        .latitude(pointOfInterest.getLatitude())
                        .longitude(pointOfInterest.getLongitude())
                        .type(pointOfInterest.getType())
                        .openTime(pointOfInterest.getOpenTime())
                        .closeTime(pointOfInterest.getOpenTime())
                        .createdAt(pointOfInterest.getCreatedAt())
                        .updatedAt(pointOfInterest.getCreatedAt())
                        .build())
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(pointOfInterestResponseList);
    }

    @PostMapping("/add")
    public ResponseEntity<PointOfInterestResponse> createPOI(@RequestBody PointOfInterestRequest request) {
        PointOfInterest pointOfInterestCreated = pointOfInterestService.createPOI(request.toPOI(), request.authorFirstName(), request.authorLastName());
        PointOfInterestResponse response = PointOfInterestResponse
                .builder()
                .name(pointOfInterestCreated.getName())
                .description(pointOfInterestCreated.getDescription())
                .author(pointOfInterestCreated.getAuthor().getName())
                .latitude(pointOfInterestCreated.getLatitude())
                .longitude(pointOfInterestCreated.getLongitude())
                .type(pointOfInterestCreated.getType())
                .openTime(pointOfInterestCreated.getOpenTime())
                .closeTime(pointOfInterestCreated.getOpenTime())
                .createdAt(pointOfInterestCreated.getCreatedAt())
                .updatedAt(pointOfInterestCreated.getCreatedAt())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<PointOfInterestResponse> editPOI(@PathVariable int id, @RequestBody PointOfInterest pointOfInterest) {
        PointOfInterest updatedPOI = pointOfInterestService.updatePOI(id, pointOfInterest);
        PointOfInterestResponse response = PointOfInterestResponse
                .builder()
                .name(updatedPOI.getName())
                .description(updatedPOI.getDescription())
                .author(updatedPOI.getAuthor().getName())
                .latitude(updatedPOI.getLatitude())
                .longitude(updatedPOI.getLongitude())
                .type(updatedPOI.getType())
                .openTime(updatedPOI.getOpenTime())
                .closeTime(updatedPOI.getOpenTime())
                .createdAt(updatedPOI.getCreatedAt())
                .updatedAt(updatedPOI.getCreatedAt())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePOI(@PathVariable int id) {
        pointOfInterestService.deletePOI(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteAll() {
        this.pointOfInterestService.deleteAllPOIs();
        return ResponseEntity.noContent().build();
    }
}
