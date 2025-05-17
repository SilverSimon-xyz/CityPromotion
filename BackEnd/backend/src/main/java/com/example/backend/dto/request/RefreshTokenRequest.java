package com.example.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@ToString
public class RefreshTokenRequest {
    @JsonProperty("refreshToken")
    private String refreshToken;

}
