package com.example.backend.security.jwt;

import com.example.backend.entities.users.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secret;
    @Value("${security.jwt-authorities-key}")
    private String authoritiesKey;
    @Value("${security.jwt-expiration-milliseconds}")
    private long expiresIn;

    //get username from JWT token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
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
        String authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .header()
                .add("typ", "JWT")
                .and()
                .claims(claims)
                .subject(user.getEmail())
                .claim(authoritiesKey, authorities)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith((SecretKey) key(), Jwts.SIG.HS256)
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public long getExpirationTime() {
        return this.expiresIn;
    }

    UsernamePasswordAuthenticationToken getAuthenticationToken(final String token, final Authentication existingAuth, final UserDetails userDetails) {
        final Claims claims = Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token).getPayload();
        final Collection<? extends GrantedAuthority> authorities = Arrays.stream(
                claims.get("role").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .toList();
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

}
