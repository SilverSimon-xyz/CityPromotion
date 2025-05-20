package com.example.backend.security.jwt;

import com.example.backend.entities.users.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secret;
    @Value("${security.jwt-expiration-milliseconds}")
    private long expiresIn;

    //get username from JWT token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    //generate JWT token
    public String generateToken(User user) {
        return buildToken(new HashMap<>(), user, expiresIn);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //validate JWT token
    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            if (!isTokenExpired(token) && username.equals(userDetails.getUsername())) {
                return true;
            }
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
        return false;
    }

    private String buildToken(Map<String, Object> claims, User user, long expiration) {

        return Jwts.builder()
                .claims(claims)
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key())
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public long getExpirationTime() {
        return this.expiresIn;
    }

}
