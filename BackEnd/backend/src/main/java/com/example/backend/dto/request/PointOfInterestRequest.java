package com.example.backend.dto.request;

import com.example.backend.entities.poi.PointOfInterest;
import com.example.backend.entities.poi.PointOfInterestType;

import java.time.LocalTime;

public record PointOfInterestRequest(String name, String description, String authorFirstname, String authorLastname,
                                     double latitude, double longitude,
                                     PointOfInterestType type, LocalTime openTime, LocalTime closeTime) {

    public PointOfInterest toPOI() {

        return PointOfInterest.builder()
                .name(name)
                .description(description)
                .latitude(latitude)
                .longitude(longitude)
                .type(type)
                .openTime(openTime)
                .closeTime(closeTime)
                .build();
    }
}
