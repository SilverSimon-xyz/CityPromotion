package com.example.backend.dto.response;

import com.example.backend.entities.users.Role;
import lombok.Builder;

@Builder
public record RoleResponse(String name, String description) {
    public static RoleResponse mapToResponse(Role role) {
        return RoleResponse
                .builder()
                .name(role.getName())
                .description(role.getDescription())
                .build();
    }
}
