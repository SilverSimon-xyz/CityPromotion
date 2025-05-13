package com.example.backend.controller;

import com.example.backend.dto.response.RoleResponse;
import com.example.backend.entities.users.User;
import com.example.backend.dto.response.AccountResponse;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
                .map(user -> AccountResponse
                        .builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .roles(user.getRoles().stream().map(
                                role ->
                                        RoleResponse
                                            .builder()
                                            .name(role.getName())
                                            .description(role.getDescription())
                                            .build()
                        )
                                .collect(Collectors.toSet()))
                        .createdAt(user.getCreatedAt())
                        .updatedAt(user.getUpdatedAt())
                        .build()
                )
                .toList();
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<AccountResponse> getUserDetails(@PathVariable int id) {
        User user = userService.getById(id);
        AccountResponse response = AccountResponse
                .builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(
                                role ->
                                        RoleResponse
                                                .builder()
                                                .name(role.getName())
                                                .description(role.getDescription())
                                                .build()
                        )
                        .collect(Collectors.toSet()))
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/add")
    public ResponseEntity<AccountResponse> createUser(@RequestBody User user) {
        User newUser = userService.createUser(user);
        AccountResponse response = AccountResponse
                .builder()
                .id(newUser.getId())
                .name(newUser.getName())
                .email(newUser.getEmail())
                .password(newUser.getPassword())
                .roles(newUser.getRoles().stream().map(
                                role ->
                                        RoleResponse
                                                .builder()
                                                .name(role.getName())
                                                .description(role.getDescription())
                                                .build()
                        )
                        .collect(Collectors.toSet()))
                .createdAt(newUser.getCreatedAt())
                .updatedAt(newUser.getUpdatedAt())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<AccountResponse> editUserName(@PathVariable int id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        AccountResponse response = AccountResponse
                .builder()
                .id(updatedUser.getId())
                .name(updatedUser.getName())
                .email(updatedUser.getEmail())
                .password(updatedUser.getPassword())
                .roles(updatedUser.getRoles().stream().map(
                                role ->
                                        RoleResponse
                                                .builder()
                                                .name(role.getName())
                                                .description(role.getDescription())
                                                .build()
                        )
                        .collect(Collectors.toSet()))
                .createdAt(updatedUser.getCreatedAt())
                .updatedAt(updatedUser.getUpdatedAt())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
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
