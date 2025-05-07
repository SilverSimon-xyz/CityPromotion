package com.example.backend.controller;

import com.example.backend.dto.response.PointOfInterestResponse;
import com.example.backend.dto.request.PointOfInterestRequest;
import com.example.backend.entities.poi.PointOfInterest;
import com.example.backend.entities.poi.PointOfInterestType;
import com.example.backend.service.PointOfInterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/poi")
public class PointOfInterestController {

    @Autowired
    private PointOfInterestService pointOfInterestService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('PRIVILEGE_READ')")
    public ResponseEntity<List<PointOfInterestResponse>> getAllPOIs() {
        List<PointOfInterestResponse> pointOfInterestResponseList = pointOfInterestService.getAllPOIs()
                .stream()
                .map(PointOfInterestResponse::mapPOIToResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(pointOfInterestResponseList);
    }

    @GetMapping("/find/{id}")
    @PreAuthorize("hasAuthority('PRIVILEGE_READ')")
    public ResponseEntity<PointOfInterestResponse> getPOIDetailsById(@PathVariable int id) {
        PointOfInterest pointOfInterest = pointOfInterestService.getPOIById(id);
        return ResponseEntity.status(HttpStatus.OK).body(PointOfInterestResponse.mapPOIToResponse(pointOfInterest));
    }

    @GetMapping("/find/name")
    @PreAuthorize("hasAuthority('PRIVILEGE_READ')")
    public ResponseEntity<List<PointOfInterestResponse>> getPOIDetailsByName(@RequestParam String name) {
        List<PointOfInterest> pointOfInterestList = pointOfInterestService.searchPOIByName(name);
        List<PointOfInterestResponse> pointOfInterestResponseList = pointOfInterestList
                        .stream()
                        .map(PointOfInterestResponse::mapPOIToResponse)
                        .toList();
        return ResponseEntity.status(HttpStatus.OK).body(pointOfInterestResponseList);
    }

    @GetMapping("/find/type")
    @PreAuthorize("hasAuthority('PRIVILEGE_READ')")
    public ResponseEntity<List<PointOfInterestResponse>> getPOIDetailsByType(@RequestParam PointOfInterestType type) {
        List<PointOfInterest> pointOfInterestList = pointOfInterestService.searchPOIByType(type);
        List<PointOfInterestResponse> pointOfInterestResponseList = pointOfInterestList
                .stream()
                .map(PointOfInterestResponse::mapPOIToResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(pointOfInterestResponseList);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('PRIVILEGE_CREATE')")
    public ResponseEntity<PointOfInterestResponse> createPOI(@RequestBody PointOfInterestRequest request) {
        PointOfInterest pointOfInterestCreated = pointOfInterestService.createPOI(request.toPOI(), request.authorName());
        return ResponseEntity.status(HttpStatus.CREATED).body(PointOfInterestResponse.mapPOIToResponse(pointOfInterestCreated));
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('PRIVILEGE_UPDATE')")
    public ResponseEntity<PointOfInterestResponse> editPOI(@PathVariable int id, @RequestBody PointOfInterest pointOfInterest) {
        PointOfInterest updatedPOI = pointOfInterestService.updatePOI(id, pointOfInterest);
        return ResponseEntity.status(HttpStatus.OK).body(PointOfInterestResponse.mapPOIToResponse(updatedPOI));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('PRIVILEGE_DELETE')")
    public ResponseEntity<Void> deletePOI(@PathVariable int id) {
        pointOfInterestService.deletePOI(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/all")
    @PreAuthorize("hasAuthority('PRIVILEGE_DELETE')")
    public ResponseEntity<Void> deleteAll() {
        this.pointOfInterestService.deleteAllPOIs();
        return ResponseEntity.noContent().build();
    }
}
