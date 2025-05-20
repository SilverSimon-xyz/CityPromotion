package com.example.backend.dto.response;

import com.example.backend.entities.content.FormatFileType;
import com.example.backend.entities.content.MediaFile;
import com.example.backend.entities.content.MultimediaContent;
import com.example.backend.entities.content.Status;
import com.example.backend.entities.poi.PointOfInterest;
import lombok.Builder;

import java.util.Date;

@Builder
public record MultimediaContentResponse(Long id, String title, FormatFileType type, String description, String author,
                                        Status status, MediaFileResponse mediaFileResponse, PointOfInterestResponse pointOfInterestResponse,
                                        Date createdAt,
                                        Date updatedAt) {

    public static MultimediaContentResponse mapToResponse(MultimediaContent multimediaContent) {
        MediaFile mediaFile = multimediaContent.getMediaFile();
        PointOfInterest pointOfInterest = multimediaContent.getPoi();
        return MultimediaContentResponse
                .builder()
                .id(multimediaContent.getId())
                .title(multimediaContent.getTitle())
                .type(multimediaContent.getType())
                .description(multimediaContent.getDescription())
                .author(multimediaContent.getAuthor().getName())
                .status(multimediaContent.getStatus())
                .mediaFileResponse(MediaFileResponse.mapToResponse(mediaFile))
                .pointOfInterestResponse(PointOfInterestResponse.mapToResponse(pointOfInterest))
                .createdAt(multimediaContent.getCreatedAt())
                .updatedAt(multimediaContent.getUpdatedAt())
                .build();
    }

}
