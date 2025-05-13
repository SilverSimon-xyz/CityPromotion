package com.example.backend.dto.request;

import com.example.backend.entities.content.FormatFileType;
import com.example.backend.entities.content.MediaFile;
import com.example.backend.entities.content.MultimediaContent;

public record MultimediaContentRequest(String title, FormatFileType type, String description, String authorFirstName, String authorLastName,
                                       MediaFile mediaFile) {

    public MultimediaContent toMultimediaContent() {
        return MultimediaContent.builder()
                .title(this.title())
                .type(this.type())
                .description(this.description())
                .build();
    }
}
