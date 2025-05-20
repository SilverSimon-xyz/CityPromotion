package com.example.backend.dto.response;

import com.example.backend.entities.users.Role;
import lombok.Builder;

@Builder
public record RoleResponse(Long id, String name, String description) {
    public static RoleResponse mapToResponse(Role role) {
        return RoleResponse
                .builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .build();
    }
}
