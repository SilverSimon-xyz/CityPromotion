package com.example.backend.dto.request;

import com.example.backend.entities.content.FormatFileType;
import com.example.backend.entities.content.MediaFile;
import com.example.backend.entities.content.MultimediaContent;

public record MultimediaContentRequest(String title, FormatFileType type, String description, String authorName,
                                       MediaFile mediaFile) {

    public MultimediaContent toMultimediaContent() {
        MultimediaContent multimediaContent = new MultimediaContent();
        multimediaContent.setTitle(title());
        multimediaContent.setType(type());
        multimediaContent.setDescription(description());
        return multimediaContent;
    }
}
