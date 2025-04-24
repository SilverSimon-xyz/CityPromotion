package com.example.backend.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private long expiresIn;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

}
