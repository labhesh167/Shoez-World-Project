package com.example.ShoezWorld.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.security.Key;

@Component
public class jwtUtil {

    private final Key key = Keys.hmacShaKeyFor("ShoezWorldSecretKeyWhichIsAtLeast32Chars!".getBytes());

    // Generate token with username and role
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role) // add role claim
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 36000000)) // 10 hours
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Validate token
    public boolean validateToken(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    // Extract username from token
    public String extractUsername(String token) {
        return parseToken(token).getBody().getSubject();
    }

    // Extract role from token
    public String extractRole(String token) {
        return parseToken(token).getBody().get("role", String.class);
    }

    // Check if token expired
    public boolean isTokenExpired(String token) {
        return parseToken(token).getBody().getExpiration().before(new Date());
    }

    // Parse and validate token
    private Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }
}
