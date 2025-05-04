package com.example.backend.utility.builder;

import com.example.backend.entities.content.MediaFile;
import com.example.backend.entities.content.MultimediaContent;
import com.example.backend.entities.users.User;

public class MultimediaContentBuilder {

    public static MultimediaContent build(MultimediaContent multimediaContentDetails, User author, MediaFile mediaFile) {
        MultimediaContent multimediaContent = new MultimediaContent();

        multimediaContent.setId(multimediaContentDetails.getId());
        multimediaContent.setTitle(multimediaContentDetails.getTitle());
        multimediaContent.setDescription(multimediaContentDetails.getDescription());
        multimediaContent.setStatus(multimediaContentDetails.getStatus());

        multimediaContent.setAuthor(author);
        multimediaContent.setMediaFile(mediaFile);

        return multimediaContent;
    }
}
