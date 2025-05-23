package com.example.backend.dto.response;

import com.example.backend.entities.content.Content;
import com.example.backend.entities.content.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.util.Date;

@Builder
public record ContentResponse(Long id, String title, String content, String hashtag, AccountResponse author,
                              Status status, PointOfInterestResponse pointOfInterest, MediaFileResponse mediaFile,
                              @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")Date createdAt,
                              @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")Date updatedAt) {

    public static ContentResponse mapToResponse(Content content) {
        PointOfInterestResponse pointOfInterestResponse = PointOfInterestResponse.mapToResponse(content.getPoi());
        AccountResponse authorResponse = AccountResponse.mapToResponse(content.getAuthor());
        MediaFileResponse mediaFileResponse = MediaFileResponse.mapToResponse(content.getMediaFile());
        return ContentResponse
                .builder()
                .id(content.getId())
                .title(content.getTitle())
                .content(content.getContent())
                .hashtag(content.getHashtag())
                .author(authorResponse)
                .status(content.getStatus())
                .pointOfInterest(pointOfInterestResponse)
                .mediaFile(mediaFileResponse)
                .createdAt(content.getCreatedAt())
                .updatedAt(content.getUpdatedAt())
                .build();
    }

}
