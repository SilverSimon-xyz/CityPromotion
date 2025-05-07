package com.example.backend.dto.response;

import com.example.backend.entities.users.Role;
import com.example.backend.entities.users.RoleType;
import com.example.backend.entities.users.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record RoleResponse(int id, RoleType name, String description, Set<String> users, Set<String> privileges) {

    public static RoleResponse mapRoleToResponse(Role role) {
        return new RoleResponse(
                role.getId(),
                role.getName(),
                role.getDescription(),

                role.getUsers()
                .stream()
                        .map(User::getEmail)
                        .collect(Collectors.toSet()),
                role.getPrivileges()
                .stream()
                        .map(privilege -> privilege.getName().name())
                        .collect(Collectors.toSet()));

    }
}
