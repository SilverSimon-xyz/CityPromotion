package com.example.backend.dto.response;

import com.example.backend.entities.content.MediaFile;

public record MediaFileResponse(int id, String name, String type, byte[] data) {

    public static MediaFileResponse mapMediaFileToResponse(MediaFile mediaFile) {
        return new MediaFileResponse(
                mediaFile.getId(),
                mediaFile.getName(),
                mediaFile.getType(),
                mediaFile.getData()
        );
    }
}
