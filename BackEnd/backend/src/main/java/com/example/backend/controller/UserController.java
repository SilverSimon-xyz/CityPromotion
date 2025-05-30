package com.example.backend.controller;

import com.example.backend.dto.request.UserRequest;
import com.example.backend.dto.response.RoleResponse;
import com.example.backend.entities.users.User;
import com.example.backend.dto.response.AccountResponse;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<AccountResponse> getAllUsers() {
        return this.userService.getAllUsers()
                .stream()
                .map(AccountResponse::mapToResponse)
                .toList();
    }

    @GetMapping("/find/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountResponse> getUserDetails(@PathVariable Long id) {
        User user = userService.getById(id);
        AccountResponse response = AccountResponse.mapToResponse(user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/find/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleResponse> getUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = (User) userService.loadUserByUsername(userDetails.getUsername());
        RoleResponse response = RoleResponse.mapToResponse(user.getRole());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountResponse> createUser(@RequestBody UserRequest request) {
        User newUser = userService.createUser(request);
        AccountResponse response = AccountResponse.mapToResponse(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountResponse> editUser(@PathVariable Long id, @RequestBody UserRequest request) {
        User updatedUser = userService.updateUser(id, request);
        AccountResponse response = AccountResponse.mapToResponse(updatedUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean isDeleted = userService.deleteUser(id);
        return isDeleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
