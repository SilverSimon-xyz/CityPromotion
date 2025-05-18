package com.example.backend.service;

import com.example.backend.entities.users.RefreshToken;
import com.example.backend.entities.users.User;
import com.example.backend.repository.RefreshTokenRepository;
import com.example.backend.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private void createRefreshToken(String token, User user) {
        Optional<RefreshToken> existingToken = refreshTokenRepository.findByUser(user);
        existingToken.ifPresent(refreshTokenRepository::delete);
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(token)
                .expired(false)
                .revoked(false)
                .issuedAt(new Date())
                .expiryAt(new Date(System.currentTimeMillis()+expiresIn))
                .build();
        refreshTokenRepository.save(refreshToken);
    }

    public String generateRefreshToken(String username) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new EntityNotFoundException("User not found!"));
        String refreshToken = Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+expiresIn))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)))
                .compact();
        createRefreshToken(refreshToken, user);
        return refreshToken;
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
