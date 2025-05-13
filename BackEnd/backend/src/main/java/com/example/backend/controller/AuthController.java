package com.example.backend.controller;

import com.example.backend.dto.request.RefreshTokenRequest;
import com.example.backend.dto.request.RegistrationRequest;
import com.example.backend.dto.response.AuthResponse;
import com.example.backend.dto.request.AuthRequest;
import com.example.backend.dto.response.RoleResponse;
import com.example.backend.entities.users.RefreshToken;
import com.example.backend.entities.users.User;
import com.example.backend.security.jwt.JwtService;
import com.example.backend.dto.response.AccountResponse;
import com.example.backend.service.AuthService;
import com.example.backend.service.RefreshTokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/registration")
    public ResponseEntity<AccountResponse> registrationHandler(@Valid @RequestBody RegistrationRequest registrationRequest) throws Exception {

        User registeredUser = authService.registration(registrationRequest);

        AccountResponse registredAccountResponse = AccountResponse
                .builder()
                .id(registeredUser.getId())
                .name(registeredUser.getName())
                .email(registeredUser.getEmail())
                .password(registeredUser.getPassword())
                .roles(registeredUser.getRoles().stream().map(
                                role ->
                                        RoleResponse
                                                .builder()
                                                .name(role.getName())
                                                .description(role.getDescription())
                                                .build()
                        )
                        .collect(Collectors.toSet()))
                .createdAt(registeredUser.getCreatedAt())
                .updatedAt(registeredUser.getUpdatedAt())
                .build();

        return new ResponseEntity<>(registredAccountResponse, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody AuthRequest authRequest) {
        try {
            User authenticatedUser = authService.login(authRequest);
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.email());
            AuthResponse response = new AuthResponse(
                    jwtService.generateToken(authenticatedUser),
                    refreshToken.getToken(),
                    jwtService.getExpirationTime());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/refresh-token")
    public AuthResponse refreshTokenHandler(@RequestBody RefreshTokenRequest request) {
        return refreshTokenService.findByToken(request.refreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateToken(user);
                    return AuthResponse.builder()
                            .accessToken(accessToken)
                            .token(request.refreshToken())
                            .expiresIn(jwtService.getExpirationTime())
                            .build();
                }).orElseThrow(() -> new RuntimeException("Refresh Token is not in DB..!!"));
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logoutHandler(@RequestBody String token) {
        refreshTokenService.revokeToken(token);
        return ResponseEntity.ok().body("Logout done!");
    }

}
