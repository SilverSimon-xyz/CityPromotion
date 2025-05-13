package com.example.backend.dto.response;

import lombok.Builder;

@Builder
public record MediaFileResponse(String name, String type, byte[] data) {


}
