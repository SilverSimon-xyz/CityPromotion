package com.example.user_spring.security.jwt;

import com.example.user_spring.security.services.Account;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    //generate JWT token
    public String generateToken(Account account) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, account, expiresIn);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //validate JWT token
    public boolean validateToken(String token, Account account) {
        final String username = extractUsername(token);
        return (username.equals(account.getUsername()) && !isTokenExpired(token));
    }

    private String createToken(Map<String, Object> claims, Account account, long expiration) {
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + expiration * 1000L);
        return Jwts.builder()
                .claims(claims)
                .subject(account.getUsername())
                .issuedAt(currentDate)
                .expiration(expireDate)
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
