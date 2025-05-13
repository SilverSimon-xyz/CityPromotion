package com.example.backend.dto.response;

import lombok.Builder;

@Builder
public record RoleResponse(String name, String description) {

}
