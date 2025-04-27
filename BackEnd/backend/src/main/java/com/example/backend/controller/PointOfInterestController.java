package com.example.backend.controller;

import com.example.backend.dto.PointOfInterestDto;
import com.example.backend.entities.PointOfInterest;
import com.example.backend.service.PointOfInterestService;
import com.example.backend.utility.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/poi")
public class PointOfInterestController {

    @Autowired
    private PointOfInterestService pointOfInterestService;
    @Autowired
    private Mapper mapper;

    @GetMapping("/all")
    public List<PointOfInterestDto> getAllPOIs() {
        return this.pointOfInterestService.getAllPOIs().stream().map(poi -> mapper.mapPOIToDto(poi)).collect(Collectors.toList());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<PointOfInterestDto> getPOIDetails(@PathVariable int id) {
        PointOfInterest pointOfInterest = pointOfInterestService.getPOIById(id);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.mapPOIToDto(pointOfInterest));
    }

    @PostMapping("/add")
    public ResponseEntity<PointOfInterestDto> createPOI(@RequestBody PointOfInterest pointOfInterest) {
        PointOfInterest pointOfInterestCreated = pointOfInterestService.createPOI(pointOfInterest);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.mapPOIToDto(pointOfInterestCreated));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<PointOfInterestDto> editPOI(@PathVariable int id, @RequestParam PointOfInterest pointOfInterest) {
        PointOfInterest updatedPOI = pointOfInterestService.updatePOI(id, pointOfInterest);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.mapPOIToDto(updatedPOI));
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
