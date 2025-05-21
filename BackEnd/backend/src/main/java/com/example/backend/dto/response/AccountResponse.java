package com.example.backend.dto.response;

import com.example.backend.entities.users.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Builder
public record AccountResponse(Long id, String firstname, String lastname, String email, String password, RoleResponse role,
                              @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")Date createdAt,
                              @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")Date updatedAt) {

    public static AccountResponse mapToResponse(User user) {
        RoleResponse roleResponse = RoleResponse.mapToResponse(user.getRole());
        return AccountResponse
                .builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(roleResponse)
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

}
