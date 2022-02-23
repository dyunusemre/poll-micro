package com.yundi.pollauthservice.security.service;

import com.yundi.pollauthservice.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (accessToken == null || !accessToken.startsWith("Bearer ")) {
            request.setAttribute("jwt", "Not valid jwt");
            filterChain.doFilter(request, response);
            return;
        }
        String token = accessToken.substring("Bearer ".length());
        if (jwtUtil.isInvalidToken(token)) {
            request.setAttribute("jwt", "Not valid jwt");
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtUtil.extractUsername(token);
        String role = jwtUtil.extractRole(token);
        if (username == null || role == null) {
            request.setAttribute("jwt", "Not valid jwt");
            filterChain.doFilter(request, response);
            return;
        }

        findUserAndSetAuthentication(username, role);
        filterChain.doFilter(request, response);
    }

    private void findUserAndSetAuthentication(String username, String role) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, Collections.singleton(new SimpleGrantedAuthority(role)));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
