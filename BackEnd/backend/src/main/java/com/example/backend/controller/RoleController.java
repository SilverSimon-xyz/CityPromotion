package com.example.backend.controller;

import com.example.backend.entities.Role;
import com.example.backend.entities.enums.RoleType;
import com.example.backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;


    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Role> getAllRoles() {
        return this.roleService.getAllRole();
    }

    @GetMapping("/find/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Role> getRoleDetails(@PathVariable int id) {
        Role role = roleService.getRoleById(id);
        return ResponseEntity.status(HttpStatus.OK).body(role);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Role> addRole(@RequestBody Role role) {
        Role newRole = roleService.addRole(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRole);
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Role> editRole(@PathVariable int id, @RequestBody Role role) {
        Role updatedRole = roleService.updateRole(id, role);
        return ResponseEntity.status(HttpStatus.OK).body(updatedRole);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRole(@PathVariable int id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/add/{id}")
    public ResponseEntity<Void> assignRoleToUser(@PathVariable int id, @RequestParam RoleType name) {
        this.roleService.assignRoleToUser(id, name);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<Void> removeRoleToUser(@PathVariable int id, @RequestParam RoleType name) {
        this.roleService.removeRoleToUser(id, name);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
