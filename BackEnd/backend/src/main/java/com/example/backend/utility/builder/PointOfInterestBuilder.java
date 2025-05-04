package com.example.backend.utility.builder;

import com.example.backend.entities.poi.PointOfInterest;

public class PointOfInterestBuilder {

    public static PointOfInterest build(PointOfInterest pointOfInterest) {
        return new PointOfInterest(
                pointOfInterest.getId(),
                pointOfInterest.getName(),
                pointOfInterest.getDescription(),
                pointOfInterest.getAuthor(),
                pointOfInterest.getLatitude(),
                pointOfInterest.getLongitude(),
                pointOfInterest.getOpenTime(),
                pointOfInterest.getCloseTime(),
                pointOfInterest.getType()
        );
    }
}
