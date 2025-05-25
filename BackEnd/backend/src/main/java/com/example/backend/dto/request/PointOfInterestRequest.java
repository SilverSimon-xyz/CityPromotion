package com.example.backend.dto.request;

import com.example.backend.entities.poi.PointOfInterestType;

import java.time.LocalTime;

public record PointOfInterestRequest(String name, String description, double latitude, double longitude, PointOfInterestType type, LocalTime openTime, LocalTime closeTime) {

}
