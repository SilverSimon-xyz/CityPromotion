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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

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
    public ResponseEntity<JwtResponse> loginHandler(@RequestBody AuthRequest authRequest) {
        try {
            User authenticatedUser = authService.login(authRequest);
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.email());
            JwtResponse response = JwtResponse.mapToResponse(
                    jwtService.generateToken(authenticatedUser),
                    refreshToken.getToken(),
                    jwtService.getExpirationTime());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
    public ResponseEntity<?> logoutHandler(@RequestBody RefreshTokenRequest request) { //HttpServletRequest request) {
        try {
            /**
            String body = new String(request.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("Request is arrived on backend: " + body);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(body);
            String token = jsonNode.get("refreshToken").asText();

            System.out.println("Token extract: " + token);
             */
            System.out.println("Request is arrived on backend: " + request);
            System.out.println("Token is arrived on backend: " + request.getRefreshToken());
            boolean deleted = refreshTokenService.revokeToken(request.getRefreshToken());
            return deleted?
                    ResponseEntity.ok().body("Logout done!"):
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token not Found or already deleted!!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during logout: " + e.getMessage());
        }
    }

}
