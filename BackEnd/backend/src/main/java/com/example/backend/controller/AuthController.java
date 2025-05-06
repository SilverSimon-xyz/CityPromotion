package com.example.backend.controller;

import com.example.backend.dto.request.RegistrationRequest;
import com.example.backend.dto.response.AuthResponse;
import com.example.backend.dto.request.AuthRequest;
import com.example.backend.entities.users.User;
import com.example.backend.security.jwt.JwtService;
import com.example.backend.dto.response.Account;
import com.example.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/registration")
    public ResponseEntity<Account> registrationHandler(@Valid @RequestBody RegistrationRequest registrationRequest) throws Exception {

        User registeredUser = authService.registration(registrationRequest);

        Account registredAccount = new Account(registeredUser);

        return new ResponseEntity<>(registredAccount, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody AuthRequest authRequest) {

        User authenticatedUser = authService.login(authRequest);

        Account account = new Account(authenticatedUser);
        String token = jwtService.generateToken(account);

        AuthResponse response = new AuthResponse(token, jwtService.getExpirationTime());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
