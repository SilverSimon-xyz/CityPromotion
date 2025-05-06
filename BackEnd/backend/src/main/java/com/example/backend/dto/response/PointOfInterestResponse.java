package com.example.backend.dto.response;
import com.example.backend.entities.poi.PointOfInterest;
import com.example.backend.entities.poi.PointOfInterestType;

import java.time.LocalTime;
import java.util.Date;

public record PointOfInterestResponse(int id, String name, String description, String author,
        double latitude, double longitude, PointOfInterestType type,
        LocalTime openTime, LocalTime closeTime, Date createdAt, Date updatedAt) {


    public static PointOfInterestResponse mapPOIToResponse(PointOfInterest pointOfInterest) {
        return new PointOfInterestResponse(
                pointOfInterest.getId(), pointOfInterest.getName(), pointOfInterest.getDescription(), pointOfInterest.getAuthor().getName(),
                pointOfInterest.getLatitude(), pointOfInterest.getLongitude(),
                pointOfInterest.getType(),
                pointOfInterest.getOpenTime(), pointOfInterest.getCloseTime(),
                pointOfInterest.getCreatedAt(), pointOfInterest.getUpdatedAt()
        );

    }

}
