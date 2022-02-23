package com.yundi.pollapigw.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

@Component
public class JwtUtil {

    @Value("${token.expire.duration}")
    private Long tokenExpireDuration;

    @Value("${token.hidden.secret}")
    private String tokenHiddenSecret;

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role").toString().replaceFirst("\\[", "").replaceAll("]", "");
    }

    public boolean isInvalidToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims == null || claims.getSubject().isEmpty() || isTokenExpired(token);
    }

    private Key getSecretKey(String secret) {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey(tokenHiddenSecret))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return extractAllClaims(token).getExpiration();
    }
}
