package com.yundi.pollauthservice.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

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
        return claims == null || isTokenExpired(token);
    }

    public String createAccessTokenByRefreshToken(String refreshToken) {
        Claims claims = extractClaims(refreshToken);
        return createTokenByClaims(claims);
    }

    private Claims extractClaims(String token) {
        return extractAllClaims(token);
    }

    private String createTokenByClaims(Claims claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(claims.getSubject())
                .setIssuedAt(getIssueDate())
                .setExpiration(getExpirationDateByTokenType("access_token"))
                .signWith(getSecretKey(tokenHiddenSecret))
                .compact();
    }

    public String createTokenByUserDetailsAndTokenType(UserDetails userDetails, String tokenType) {
        return Jwts.builder()
                .setClaims(claims(userDetails))
                .setSubject(userDetails.getUsername())
                .setIssuedAt(getIssueDate())
                .setExpiration(getExpirationDateByTokenType(tokenType))
                .signWith(getSecretKey(tokenHiddenSecret))
                .compact();
    }

    private Map<String, Object> claims(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()).get(0));
        return claims;
    }

    private Date getExpirationDateByTokenType(String tokenType) {
        return new Date("access_token".equals(tokenType)
                ? tokenExpireDuration * 500 + System.currentTimeMillis()
                : tokenExpireDuration * 1000 + System.currentTimeMillis());
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
