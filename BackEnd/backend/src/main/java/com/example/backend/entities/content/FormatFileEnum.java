package com.example.backend.entities.content;

public enum FormatFileEnum {
    DOCUMENT,
    IMAGE,
    AUDIO,
    VIDEO,
    OTHER;

    public static FormatFileEnum formatFile(String format) {
        switch (format.toLowerCase()) {
            //Categoria Documenti: pdf, doc, txt, odt
            case "pdf":
            case "doc":
            case "txt":
            case "odt":
                return DOCUMENT;

            //Categoria Immagini: jpeg, jpg, png, gif
            case "jpeg":
            case "jpg":
            case "png":
            case "gif":
                return IMAGE;

            //Categoria Audio: mp3, wav, aac, flac
            case "mp3":
            case "wav":
            case "aac":
            case "flac":
                return AUDIO;

            //Categoria Video: mp4, avi, mkv, mov
            case "mp4":
            case "avi":
            case "mkv":
            case "mov":
                return VIDEO;

            default:
                // Se non viene riconosciuto, si può scegliere un default
                return OTHER;
        }
    }
}
