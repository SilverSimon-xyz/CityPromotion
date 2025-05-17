package com.example.backend.service;

import com.example.backend.entities.users.RefreshToken;
import com.example.backend.entities.users.User;
import com.example.backend.repository.RefreshTokenRepository;
import com.example.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    public RefreshToken createRefreshToken(String username) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new EntityNotFoundException("User not found!"));
        Optional<RefreshToken> existingToken = refreshTokenRepository.findByUser(user);
        existingToken.ifPresent(refreshTokenRepository::delete);
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plus(Duration.ofDays(7)))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if(token.getExpiryDate().compareTo(Instant.now())<0) {
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
            System.out.println("Token arrived on service: " + optionalToken.get());
            refreshTokenRepository.deleteByToken(token);
            SecurityContextHolder.clearContext();
            return true;
        } catch (RuntimeException e) {
            throw new RuntimeException("Error during cancellation of token " + e );
        }
    }

}
