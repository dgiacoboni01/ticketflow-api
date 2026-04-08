package com.ingressos.api.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private static final long EXPIRATION_MS = 86_400_000L; // 24 horas

    private SecretKey chave() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String gerar(String email, String role, Long userId) {
        return Jwts.builder()
            .subject(email)
            .claim("role", role)
            .claim("userId", userId)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
            .signWith(chave())
            .compact();
    }

    public Claims claims(String token) {
        return Jwts.parser()
            .verifyWith(chave())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    public boolean valido(String token) {
        try { claims(token); return true; } catch (JwtException e) { return false; }
    }

    public String email(String token)  { return claims(token).getSubject(); }
    public String role(String token)   { return claims(token).get("role", String.class); }
    public Long   userId(String token) { return claims(token).get("userId", Long.class); }
}
