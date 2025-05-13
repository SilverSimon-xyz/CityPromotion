package com.example.backend.dto.response;

import lombok.*;

import java.util.Date;
import java.util.Set;

@Builder
public record AccountResponse(int id, String name, String email, String password, Set<RoleResponse> roles,
                              Date createdAt, Date updatedAt) {

}
