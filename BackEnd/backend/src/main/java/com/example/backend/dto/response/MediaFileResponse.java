package com.example.backend.dto.response;

import com.example.backend.entities.content.MediaFile;
import lombok.Builder;

import java.util.Base64;

@Builder
public record MediaFileResponse(Long id, String name, String type, String base64) {

    public static MediaFileResponse mapToResponse(MediaFile mediaFile) {
        return MediaFileResponse.builder()
                .id(mediaFile.getId())
                .name(mediaFile.getName())
                .type(mediaFile.getType())
                .base64(Base64.getEncoder().encodeToString(mediaFile.getData()))
                .build();
    }
}
