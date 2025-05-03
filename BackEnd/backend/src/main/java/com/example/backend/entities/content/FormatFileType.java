package com.example.backend.entities.content;

public enum FormatFileType {

    DOCUMENT,
    IMAGE,
    AUDIO,
    VIDEO,
    OTHER;

    public static FormatFileType formatFile(String format) {
        return switch (format.toLowerCase()) {
            //Document: pdf, doc, txt, odt
            case "pdf", "doc", "txt", "odt" -> DOCUMENT;

            //Images: jpeg, jpg, png, gif
            case "jpeg", "jpg", "png", "gif" -> IMAGE;

            //Audio: mp3, wav, aac, flac
            case "mp3", "wav", "aac", "flac" -> AUDIO;

            //Video: mp4, avi, mkv, mov
            case "mp4", "avi", "mkv", "mov" -> VIDEO;

            default -> OTHER;
        };
    }
}
