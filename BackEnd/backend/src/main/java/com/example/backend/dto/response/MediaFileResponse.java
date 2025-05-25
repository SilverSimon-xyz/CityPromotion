package com.example.backend.dto.response;

import com.example.backend.entities.content.MediaFile;
import lombok.Builder;


@Builder
public record MediaFileResponse(Long id, String name, String type, long size, byte[] data) {

    public static MediaFileResponse mapToResponse(MediaFile mediaFile) {
        return MediaFileResponse.builder()
                .id(mediaFile.getId())
                .name(mediaFile.getName())
                .type(mediaFile.getType())
                .size(mediaFile.getSize())
                .data(mediaFile.getData())
                .build();
    }
}
