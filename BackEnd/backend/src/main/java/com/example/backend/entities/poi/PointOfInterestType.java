package com.example.backend.entities.poi;

public enum PointOfInterestType {
    Turismo,
    Alloggio,
    Servizio,
    Natura,
    Altro;

    public static PointOfInterestType fromString(String type) {
        return switch (type.toLowerCase()) {
            case "turismo" -> Turismo;
            case "alloggio" -> Alloggio;
            case "servizio" -> Servizio;
            case "natura" -> Natura;
            default -> Altro;
        };
    }
}
