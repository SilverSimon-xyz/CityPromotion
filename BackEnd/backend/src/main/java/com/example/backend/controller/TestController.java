package com.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/all")
    public ResponseEntity<String> allAccess() {
        return ResponseEntity.ok("Public Content.");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> helloAdmin(){
        return ResponseEntity.ok("Hello Admin");
    }

    @GetMapping("/moderator")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<String> helloModerator(){
        return ResponseEntity.ok("Hello Moderator");
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<String> helloUser(){
        return ResponseEntity.ok("Hello User");
    }
}
