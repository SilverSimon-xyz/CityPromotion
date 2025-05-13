package com.example.backend.dto.response;
import com.example.backend.entities.poi.PointOfInterestType;
import lombok.Builder;

import java.time.LocalTime;
import java.util.Date;

@Builder
public record PointOfInterestResponse(String name, String description, String author,
        double latitude, double longitude, PointOfInterestType type,
        LocalTime openTime, LocalTime closeTime, Date createdAt, Date updatedAt) {

}
