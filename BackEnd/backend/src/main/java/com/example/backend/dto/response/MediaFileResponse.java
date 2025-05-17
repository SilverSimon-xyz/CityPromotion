package com.example.backend.dto.response;

import com.example.backend.entities.content.MediaFile;
import lombok.Builder;

@Builder
public record MediaFileResponse(String name, String type, byte[] data) {

    public static MediaFileResponse mapToResponse(MediaFile mediaFile) {
        return MediaFileResponse.builder()
                .name(mediaFile.getName())
                .type(mediaFile.getType())
                .data(mediaFile.getData())
                .build();
    }
}
