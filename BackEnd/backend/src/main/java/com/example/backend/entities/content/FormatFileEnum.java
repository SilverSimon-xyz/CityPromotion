package com.example.backend.entities.content;

public enum FormatFileEnum {
    DOCUMENT,
    IMAGE,
    AUDIO,
    VIDEO,
    OTHER;

    public static FormatFileEnum formatFile(String format) {
        return switch (format.toLowerCase()) {
            //Categoria Documenti: pdf, doc, txt, odt
            case "pdf", "doc", "txt", "odt" -> DOCUMENT;

            //Categoria Immagini: jpeg, jpg, png, gif
            case "jpeg", "jpg", "png", "gif" -> IMAGE;

            //Categoria Audio: mp3, wav, aac, flac
            case "mp3", "wav", "aac", "flac" -> AUDIO;

            //Categoria Video: mp4, avi, mkv, mov
            case "mp4", "avi", "mkv", "mov" -> VIDEO;
            default ->
                // Se non viene riconosciuto, si può scegliere un default
                    OTHER;
        };
    }
}
