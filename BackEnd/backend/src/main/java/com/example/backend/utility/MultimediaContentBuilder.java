package com.example.backend.utility;

import com.example.backend.entities.content.MediaFile;
import com.example.backend.entities.content.MultimediaContent;
import com.example.backend.entities.poi.PointOfInterest;
import com.example.backend.entities.users.User;

public class MultimediaContentBuilder {

    public static MultimediaContent build(MultimediaContent multimediaContentDetails, User author, MediaFile mediaFile, PointOfInterest pointOfInterest) {


        return MultimediaContent.builder()
                .id(multimediaContentDetails.getId())
                .title(multimediaContentDetails.getTitle())
                .type(multimediaContentDetails.getType())
                .description(multimediaContentDetails.getDescription())
                .status(multimediaContentDetails.getStatus())
                .author(author)
                .mediaFile(mediaFile)
                .poi(pointOfInterest)
                .build();
    }
}
