package com.example.backend.dto.request;

import com.example.backend.entities.content.FormatFileType;
import com.example.backend.entities.content.MediaFile;
import com.example.backend.entities.content.MultimediaContent;

public record MultimediaContentRequest(String title, FormatFileType type, String description, String authorFirstname, String authorLastname,
                                       MediaFile mediaFile) {

    public MultimediaContent toMultimediaContent() {
        return MultimediaContent.builder()
                .title(title)
                .type(type)
                .description(description)
                .build();
    }
}
