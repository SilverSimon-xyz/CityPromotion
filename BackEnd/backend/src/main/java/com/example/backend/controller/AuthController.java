package com.example.backend.controller;

import com.example.backend.dto.request.RefreshTokenRequest;
import com.example.backend.dto.request.RegistrationRequest;
import com.example.backend.dto.response.JwtResponse;
import com.example.backend.dto.request.AuthRequest;
import com.example.backend.entities.users.RefreshToken;
import com.example.backend.entities.users.User;
import com.example.backend.security.jwt.JwtService;
import com.example.backend.dto.response.AccountResponse;
import com.example.backend.service.AuthService;
import com.example.backend.service.RefreshTokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
        AccountResponse response = AccountResponse.mapToResponse(registeredUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginHandler(@RequestBody AuthRequest authRequest) {
        try {
            User authenticatedUser = authService.login(authRequest);
            String refreshToken = refreshTokenService.generateRefreshToken(authRequest.email());
            JwtResponse response = JwtResponse.mapToResponse(
                    jwtService.generateToken(authenticatedUser),
                    refreshToken,
                    jwtService.getExpirationTime());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user-role")
    public ResponseEntity<Map<String, String>> getUserRole(@RequestHeader("Authorization") String token) {
        String role = jwtService.extractRole(token.replace("Bearer ", ""));
        Map<String, String> response = new HashMap<>();
        response.put("role", role);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public JwtResponse refreshTokenHandler(@RequestBody RefreshTokenRequest request) {
        return refreshTokenService.findByToken(request.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateToken(user);
                    return JwtResponse.mapToResponse(accessToken, request.getRefreshToken(), jwtService.getExpirationTime());
                }).orElseThrow(() -> new RuntimeException("Refresh Token is not in DB..!!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutHandler(@RequestHeader("Authorization") String token) {
        try {
            if(token == null || !token.contains(".")) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token JWT not valid. No token come to Backend");
            String refreshToken = token.replace("Bearer ", "").trim();
            return refreshTokenService.revokeToken(refreshToken)?
                    ResponseEntity
                            .status(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body("{\"message\":\"Logout done with success!\"}"):
                    ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body("Token not Found or already deleted!!");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during logout: " + e.getMessage());
        }
    }

}
