package com.example.backend.dto.response;

import com.example.backend.entities.content.FormatFileType;
import com.example.backend.entities.content.MultimediaContent;
import com.example.backend.entities.content.Status;

public record MultimediaContentResponse(int id, String title, FormatFileType type, String description, String author,
                                        Status status, MediaFileResponse mediaFileResponse, PointOfInterestResponse pointOfInterestResponse) {

    public static MultimediaContentResponse mapContentToResponse(MultimediaContent multimediaContent) {
        return new MultimediaContentResponse(
                multimediaContent.getId(),
                multimediaContent.getTitle(),
                multimediaContent.getType(),
                multimediaContent.getDescription(),
                multimediaContent.getAuthor().getName(),
                multimediaContent.getStatus(),
                MediaFileResponse.mapMediaFileToResponse(multimediaContent.getMediaFile()),
                PointOfInterestResponse.mapPOIToResponse(multimediaContent.getPoi())
                );
    }


}
