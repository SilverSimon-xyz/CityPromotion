package com.example.backend.entities.enums;

public enum PointOfInterestType {
    Turismo,
    Alloggio,
    Servizio,
    Natura,
    Altro;

    public static PointOfInterestType fromOSMTag(String osmTag) {
        return switch (osmTag.toLowerCase()) {
            case "monumenti", "musei", "quartieri_storici", "teatri", "luoghi_culto", "zone_pedonali", "planetari" ->
                    Turismo;
            case "hotels", "motels", "ostelli", "guest_house" -> Alloggio;
            case "scuole", "università", "ospedali", "farmacie", "cinema", "mercati", "ristoranti" -> Servizio;
            case "parchi", "foreste", "vette", "vigneti", "spiagge" -> Natura;
            default ->
                    Altro;
        };
    }

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
