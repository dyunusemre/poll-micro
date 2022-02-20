package com.yundi.pollauthservice.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Component
public class JwtUtil {

    @Value("${token.expire.duration}")
    private Long tokenExpireDuration;

    @Value("${token.hidden.secret}")
    private String tokenHiddenSecret;


    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isValidToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims != null && !isTokenExpired(token);
    }

    public String createAccessTokenByRefreshToken(String refreshToken) {
        String username = extractUsername(refreshToken);
        return createToken(username, "access_token");
    }

    public String createToken(String username, String tokenType) {
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(username)
                .setIssuedAt(getIssueDate())
                .setExpiration(getExpirationDateByTokenType(tokenType))
                .signWith(getSecretKey(tokenHiddenSecret))
                .compact();
    }

    private Date getExpirationDateByTokenType(String tokenType) {
        return new Date("access_token".equals(tokenType) ? tokenExpireDuration : tokenExpireDuration * 1000 + System.currentTimeMillis());
    }

    private Key getSecretKey(String secret) {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    private Date getIssueDate() {
        return new Date(System.currentTimeMillis());
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
