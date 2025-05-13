package com.example.backend.dto.response;

import lombok.Builder;

@Builder
public record AuthResponse(String accessToken, String token, long expiresIn) {

}
