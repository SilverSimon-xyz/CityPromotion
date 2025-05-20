package com.example.backend.controller;

import com.example.backend.dto.request.UserRequest;
import com.example.backend.dto.response.RoleResponse;
import com.example.backend.entities.users.User;
import com.example.backend.dto.response.AccountResponse;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<AccountResponse> getAllUsers() {
        return this.userService.getAllUsers()
                .stream()
                .map(AccountResponse::mapToResponse)
                .toList();
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<AccountResponse> getUserDetails(@PathVariable Long id) {
        User user = userService.getById(id);
        AccountResponse response = AccountResponse.mapToResponse(user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/find/role")
    public ResponseEntity<Set<RoleResponse>> getUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = (User) userService.loadUserByUsername(userDetails.getUsername());
        Set<RoleResponse> response = user.getRoles()
                .stream()
                .map(RoleResponse::mapToResponse)
                .collect(Collectors.toSet());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/add")
    public ResponseEntity<AccountResponse> createUser(@RequestBody UserRequest request) {
        User newUser = userService.createUser(request.toUser(), request.roleName());
        AccountResponse response = AccountResponse.mapToResponse(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<AccountResponse> editUser(@PathVariable Long id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        AccountResponse response = AccountResponse.mapToResponse(updatedUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean isDeleted = userService.deleteUser(id);
        return isDeleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteAll() {
        this.userService.deleteAllUsers();
        return ResponseEntity.noContent().build();
    }

}
