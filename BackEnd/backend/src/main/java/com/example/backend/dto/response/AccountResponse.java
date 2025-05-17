package com.example.backend.dto.response;

import com.example.backend.entities.users.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
public record AccountResponse(int id, String name, String email, String password, Set<RoleResponse> roles,
                              @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")Date createdAt,
                              @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Rome")Date updatedAt) {

    public static AccountResponse mapToResponse(User user) {
        return AccountResponse
                .builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles().stream()
                        .map(RoleResponse::mapToResponse)
                        .collect(Collectors.toSet()))
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

}
