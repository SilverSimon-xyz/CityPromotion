package com.example.backend.dto.response;

import lombok.Builder;

@Builder
public record JwtResponse(String accessToken, String refreshToken, long expiresIn) {

    public static JwtResponse mapToResponse(String accessToken, String refreshToken, long expiresIn) {
        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(expiresIn)
                .build();
    }
}
