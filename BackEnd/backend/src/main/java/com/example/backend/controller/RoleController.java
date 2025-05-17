package com.example.backend.controller;

import com.example.backend.dto.response.AccountResponse;
import com.example.backend.dto.response.RoleResponse;
import com.example.backend.entities.users.Role;
import com.example.backend.entities.users.User;
import com.example.backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<RoleResponse> getAllRoles() {
        Set<Role> roles = new HashSet<>(roleService.getAllRole());
        roles.forEach(role -> {
            Set<User> users = new HashSet<>(roleService.getUsersByRoleName(role.getName()));
            role.setUsers(users);
        });
        return roles.stream().map(RoleResponse::mapToResponse).toList();
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<RoleResponse> getRoleDetails(@PathVariable int id) {
        Role role = roleService.getRoleById(id);
        Set<User> users = new HashSet<>(roleService.getUsersByRoleName(role.getName()));
        role.setUsers(users);
        RoleResponse response = RoleResponse.mapToResponse(role);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/add")
    public ResponseEntity<RoleResponse> addRole(@RequestBody Role role) {
        Role newRole = roleService.addRole(role);
        RoleResponse response = RoleResponse.mapToResponse(newRole);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<RoleResponse> editRole(@PathVariable int id, @RequestBody Role role) {
        Role updatedRole = roleService.updateRole(id, role);
        RoleResponse response = RoleResponse.mapToResponse(updatedRole);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable int id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/assign/{id}/name")
    public ResponseEntity<AccountResponse> assignRoleToUser(@PathVariable int id, @RequestParam String name) {
        User user = this.roleService.assignRoleToUser(id, name);
        AccountResponse response = AccountResponse.mapToResponse(user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/remove/{id}/name")
    public ResponseEntity<Void> removeRoleToUser(@PathVariable int id, @RequestParam String name) {
        this.roleService.removeRoleToUser(id, name);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

/**
    @PutMapping("/assign/{id}/name/privilege-name")
    public ResponseEntity<RoleResponse> assignPrivilegeRoleToUser(@PathVariable int id, @RequestParam String name, @RequestParam String privilegeName) {
        Role role = this.roleService.assignPrivilegeRoleToUser(id, name, privilegeName);
        RoleResponse response = RoleResponse.mapToResponse(role);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/remove/{id}/name/privilege-name")
    public ResponseEntity<Void> removePrivilegeRoleToUser(@PathVariable int id, @RequestParam String name, @RequestParam String privilegeName) {
        this.roleService.removePrivilegeRoleToUser(id, name, privilegeName);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    */
}
