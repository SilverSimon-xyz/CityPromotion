package com.example.backend.service;

import com.example.backend.entities.users.RefreshToken;
import com.example.backend.entities.users.User;
import com.example.backend.repository.RefreshTokenRepository;
import com.example.backend.repository.UserRepository;

import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.security.Keys;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;


@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    @Value("${security.jwt.secret-key}")
    private String secret;

    @Value("${security.jwt-expiration-milliseconds}")
    private long expiresIn;

    @Autowired
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    private String createRefreshToken(User user) {
        String token = Jwts.builder()
                .header()
                .add("typ", "JWT")
                .and()
                .subject(user.getUsername())
                .claim("role", user.getRole().getName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+expiresIn))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)), Jwts.SIG.HS256)
                .compact();
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(token)
                .expired(false)
                .revoked(false)
                .issuedAt(new Date())
                .expiryAt(new Date(System.currentTimeMillis()+expiresIn))
                .build();
        refreshTokenRepository.save(refreshToken);
        return token;
    }

    public String generateRefreshToken(String username) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new EntityNotFoundException("User not found!"));
        Optional<RefreshToken> existingToken = refreshTokenRepository.findByUser(user);
        if(existingToken.isPresent()) {
            RefreshToken token = existingToken.get();
            if(token.getExpiryAt().before(new Date())) {
                refreshTokenRepository.delete(token);
                return createRefreshToken(user);
            }
            token.setExpiryAt(new Date(System.currentTimeMillis()+expiresIn));
            refreshTokenRepository.save(token);
            return token.getToken();
        } else {
            return createRefreshToken(user);
        }
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if(token.getExpiryAt().before(new Date())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expire!");
        }
        return token;
    }

    @Transactional
    public boolean revokeToken(String token) {
        Optional<RefreshToken> optionalToken = refreshTokenRepository.findByToken(token);
        if(optionalToken.isEmpty()) return false;
        try {
            refreshTokenRepository.deleteByToken(token);
            SecurityContextHolder.clearContext();
            return true;
        } catch (RuntimeException e) {
            throw new RuntimeException("Error during cancellation of token " + e );
        }
    }

}
