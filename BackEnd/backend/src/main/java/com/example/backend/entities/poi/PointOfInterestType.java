package com.example.backend.entities.poi;

public enum PointOfInterestType {
    TOURISM,
    ACCOMMODATION,
    SERVICE,
    NATURE,
    OTHER;

    public static PointOfInterestType fromString(String type) {
        return switch (type.toLowerCase()) {
            case "tourism" -> TOURISM;
            case "accommodation" -> ACCOMMODATION;
            case "service" -> SERVICE;
            case "nature" -> NATURE;
            default -> OTHER;
        };
    }
}
