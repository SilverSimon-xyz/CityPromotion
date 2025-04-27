package com.example.backend.controller;

import com.example.backend.entities.User;
import com.example.backend.dto.Account;
import com.example.backend.service.UserService;
import com.example.backend.utility.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private Mapper mapper;

    @GetMapping("/all")
    public List<Account> getAllUsers() {
        return this.userService.getAllUsers()
                .stream()
                .map(mapper::mapUserToAccount)
                .toList();
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Account> getUserDetails(@PathVariable int id) {
        User user = userService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.mapUserToAccount(user));
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Account> createUser(@RequestBody User user) {
        User newUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.mapUserToAccount(newUser));
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Account> editUserName(@PathVariable int id, @RequestParam String name) {
        User updatedUser = userService.updateUser(id, name);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.mapUserToAccount(updatedUser));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        boolean isDeleted = userService.deleteUser(id);
        return isDeleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAll() {
        this.userService.deleteAllUsers();
        return ResponseEntity.noContent().build();
    }


    //TODO: Method not tested yet: getCurrentUser()

}
