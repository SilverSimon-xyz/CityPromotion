package com.example.backend.dto.response;

import com.example.backend.entities.content.FormatFileType;
import com.example.backend.entities.content.Status;
import lombok.Builder;

@Builder
public record MultimediaContentResponse(String title, FormatFileType type, String description, String author,
                                        Status status, MediaFileResponse mediaFileResponse, PointOfInterestResponse pointOfInterestResponse) {


}
