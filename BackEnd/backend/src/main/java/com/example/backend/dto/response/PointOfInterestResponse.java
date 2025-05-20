package com.example.backend.dto.response;
import com.example.backend.entities.poi.PointOfInterest;
import com.example.backend.entities.poi.PointOfInterestType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalTime;
import java.util.Date;

@Builder
public record PointOfInterestResponse(Long id, String name, String description, String author,
                                      double latitude, double longitude, PointOfInterestType type,
                                      @JsonFormat(pattern = "HH:mm:ss")LocalTime openTime, @JsonFormat(pattern = "HH:mm:ss")LocalTime closeTime,
                                      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")Date createdAt,
                                      @JsonFormat(pattern = "yyyy-MM-d HH:mm:ss", timezone = "Europe/Rome")Date updatedAt) {

    public static PointOfInterestResponse mapToResponse(PointOfInterest pointOfInterest) {
        return PointOfInterestResponse
                .builder()
                .id(pointOfInterest.getId())
                .name(pointOfInterest.getName())
                .description(pointOfInterest.getDescription())
                .author(pointOfInterest.getAuthor().getName())
                .latitude(pointOfInterest.getLatitude())
                .longitude(pointOfInterest.getLongitude())
                .type(pointOfInterest.getType())
                .openTime(pointOfInterest.getOpenTime())
                .closeTime(pointOfInterest.getCloseTime())
                .createdAt(pointOfInterest.getCreatedAt())
                .updatedAt(pointOfInterest.getUpdatedAt())
                .build();
    }

}
