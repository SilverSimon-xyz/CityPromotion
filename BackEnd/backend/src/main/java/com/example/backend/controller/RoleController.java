package com.example.backend.controller;

import com.example.backend.dto.RoleDto;
import com.example.backend.entities.Privilege;
import com.example.backend.entities.Role;
import com.example.backend.entities.enums.PrivilegeType;
import com.example.backend.entities.enums.RoleType;
import com.example.backend.service.RoleService;
import com.example.backend.utility.Mapper;
import jakarta.persistence.EntityNotFoundException;
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
    @Autowired
    private Mapper mapper;


    @GetMapping("/all")
    //@PreAuthorize("hasRole('ADMIN')")
    public List<RoleDto> getAllRoles() {
        return this.roleService.getAllRole().stream().map(role->mapper.mapRoleToDto(role)).toList();
    }

    @GetMapping("/find/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleDto> getRoleDetails(@PathVariable int id) {
        Role role = roleService.getRoleById(id);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.mapRoleToDto(role));
    }

    @PostMapping("/add")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleDto> addRole(@RequestBody Role role) {
        Role newRole = roleService.addRole(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.mapRoleToDto(newRole));
    }

    @PutMapping("/edit/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleDto> editRole(@PathVariable int id, @RequestBody Role role) {
        Role updatedRole = roleService.updateRole(id, role);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.mapRoleToDto(updatedRole));
    }

    @DeleteMapping("/delete/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRole(@PathVariable int id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/assign/{id}/name")
    public ResponseEntity<RoleDto> assignRoleToUser(@PathVariable int id, @RequestParam RoleType name) {
        Role role = this.roleService.assignRoleToUser(id, name);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.mapRoleToDto(role));
    }

    @PutMapping("/remove/{id}/name")
    public ResponseEntity<Void> removeRoleToUser(@PathVariable int id, @RequestParam RoleType name) {
        this.roleService.removeRoleToUser(id, name);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/assign/{id}/name/privilege-name")
    public ResponseEntity<RoleDto> assignPrivilegeRoleToUser(@PathVariable int id, @RequestParam RoleType name, @RequestParam PrivilegeType privilegeName) {
        Role role = this.roleService.assignPrivilegeRoleToUser(id, name, privilegeName);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.mapRoleToDto(role));
    }

    @PutMapping("/remove/{id}/name/privilege-name")
    public ResponseEntity<Void> removePrivilegeRoleToUser(@PathVariable int id, @RequestParam RoleType name, @RequestParam PrivilegeType privilegeName) {
        this.roleService.removePrivilegeRoleToUser(id, name, privilegeName);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
