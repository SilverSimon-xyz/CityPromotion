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
                .map(PointOfInterestResponse::mapToResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(pointOfInterestResponseList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<PointOfInterestResponse> getPOIDetailsById(@PathVariable Long id) {
        PointOfInterest pointOfInterest = pointOfInterestService.getPOIById(id);
        PointOfInterestResponse response = PointOfInterestResponse.mapToResponse(pointOfInterest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PointOfInterestResponse>> searchPointOfInterest(@RequestParam(required = false) String name,
                                                                               @RequestParam(required = false) PointOfInterestType type) {
        List<PointOfInterest> pointOfInterestList = pointOfInterestService.searchPOI(name, type);
        List<PointOfInterestResponse> pointOfInterestResponseList = pointOfInterestList
                        .stream()
                        .map(PointOfInterestResponse::mapToResponse)
                        .toList();
        return ResponseEntity.status(HttpStatus.OK).body(pointOfInterestResponseList);
    }

    @PostMapping("/add")
    public ResponseEntity<PointOfInterestResponse> createPOI(@RequestBody PointOfInterestRequest request) {
        PointOfInterest pointOfInterestCreated = pointOfInterestService.createPOI(request.toPOI(), request.authorFirstname(), request.authorLastname());
        PointOfInterestResponse response = PointOfInterestResponse.mapToResponse(pointOfInterestCreated);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<PointOfInterestResponse> editPOI(@PathVariable Long id, @RequestBody PointOfInterest pointOfInterest) {
        PointOfInterest updatedPOI = pointOfInterestService.updatePOI(id, pointOfInterest);
        PointOfInterestResponse response = PointOfInterestResponse.mapToResponse(updatedPOI);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePOI(@PathVariable Long id) {
        pointOfInterestService.deletePOI(id);
        return ResponseEntity.noContent().build();
    }

}
