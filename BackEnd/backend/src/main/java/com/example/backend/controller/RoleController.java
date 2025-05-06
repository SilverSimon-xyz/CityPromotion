package com.example.backend.controller;

import com.example.backend.dto.response.RoleResponse;
import com.example.backend.entities.users.Role;
import com.example.backend.entities.users.User;
import com.example.backend.entities.users.PrivilegeType;
import com.example.backend.entities.users.RoleType;
import com.example.backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/all")
    public List<RoleResponse> getAllRoles() {
        List<Role> roles = roleService.getAllRole();
        roles.forEach(role -> {
            List<User> users = roleService.getUsersByRoleName(role.getName());
            role.setUsers(users);
        });
        return roles.stream().map(RoleResponse::mapRoleToResponse).toList();
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<RoleResponse> getRoleDetails(@PathVariable int id) {
        Role role = roleService.getRoleById(id);
        List<User> users = roleService.getUsersByRoleName(role.getName());
        role.setUsers(users);
        return ResponseEntity.status(HttpStatus.OK).body(RoleResponse.mapRoleToResponse(role));
    }

    @PostMapping("/add")
    public ResponseEntity<RoleResponse> addRole(@RequestBody Role role) {
        Role newRole = roleService.addRole(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(RoleResponse.mapRoleToResponse(role));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<RoleResponse> editRole(@PathVariable int id, @RequestBody Role role) {
        Role updatedRole = roleService.updateRole(id, role);
        return ResponseEntity.status(HttpStatus.OK).body(RoleResponse.mapRoleToResponse(role));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable int id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/assign/{id}/name")
    public ResponseEntity<RoleResponse> assignRoleToUser(@PathVariable int id, @RequestParam RoleType name) {
        Role role = this.roleService.assignRoleToUser(id, name);
        return ResponseEntity.status(HttpStatus.OK).body(RoleResponse.mapRoleToResponse(role));
    }

    @PutMapping("/remove/{id}/name")
    public ResponseEntity<Void> removeRoleToUser(@PathVariable int id, @RequestParam RoleType name) {
        this.roleService.removeRoleToUser(id, name);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/assign/{id}/name/privilege-name")
    public ResponseEntity<RoleResponse> assignPrivilegeRoleToUser(@PathVariable int id, @RequestParam RoleType name, @RequestParam PrivilegeType privilegeName) {
        Role role = this.roleService.assignPrivilegeRoleToUser(id, name, privilegeName);
        return ResponseEntity.status(HttpStatus.OK).body(RoleResponse.mapRoleToResponse(role));
    }

    @PutMapping("/remove/{id}/name/privilege-name")
    public ResponseEntity<Void> removePrivilegeRoleToUser(@PathVariable int id, @RequestParam RoleType name, @RequestParam PrivilegeType privilegeName) {
        this.roleService.removePrivilegeRoleToUser(id, name, privilegeName);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
