package com.example.backend.controller;

import com.example.backend.dto.response.RoleResponse;
import com.example.backend.entities.users.Role;
import com.example.backend.entities.users.User;
import com.example.backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<RoleResponse> getAllRoles() {
        Set<Role> roles = new HashSet<>(roleService.getAllRole());
        roles.forEach(role -> {
            Set<User> users = new HashSet<>(roleService.getUsersByRoleName(role.getName()));
            role.setUsers(users);
        });
        return roles.stream().map(RoleResponse::mapToResponse).toList();
    }

    @GetMapping("/find/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleResponse> getRoleDetails(@PathVariable Long id) {
        Role role = roleService.getRoleById(id);
        Set<User> users = new HashSet<>(roleService.getUsersByRoleName(role.getName()));
        role.setUsers(users);
        RoleResponse response = RoleResponse.mapToResponse(role);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

}
