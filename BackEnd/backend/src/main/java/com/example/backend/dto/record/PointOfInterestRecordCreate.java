package com.example.backend.dto.record;

import com.example.backend.entities.poi.PointOfInterest;
import com.example.backend.entities.poi.PointOfInterestType;

import java.time.LocalTime;

public record PointOfInterestRecordCreate(String name, String description, String authorName, double latitude, double longitude,
                                          PointOfInterestType type, LocalTime openTime, LocalTime closeTime) {

    public PointOfInterest toPOI() {
        PointOfInterest pointOfInterest = new PointOfInterest();
        pointOfInterest.setName(name);
        pointOfInterest.setDescription(description);
        pointOfInterest.setLatitude(latitude);
        pointOfInterest.setLongitude(longitude);
        pointOfInterest.setType(type);
        pointOfInterest.setOpenTime(openTime);
        pointOfInterest.setCloseTime(closeTime);
        return pointOfInterest;
    }
}
