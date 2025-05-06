package com.example.backend.dto.response;

import com.example.backend.entities.users.Role;
import com.example.backend.entities.users.RoleType;
import com.example.backend.entities.users.User;

import java.util.Collection;

public record RoleResponse(int id, RoleType name, String description, Collection<String> users,Collection<String> privileges) {

    public static RoleResponse mapRoleToResponse(Role role) {
        return new RoleResponse(
                role.getId(),
                role.getName(),
                role.getDescription(),

                role.getUsers()
                .stream()
                .map(User::getEmail)
                .toList(),
                role.getPrivileges()
                .stream()
                .map(privilege -> privilege.getName().name())
                .toList());

    }
}
