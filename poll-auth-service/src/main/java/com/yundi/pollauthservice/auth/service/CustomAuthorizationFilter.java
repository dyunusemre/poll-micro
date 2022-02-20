package com.yundi.pollauthservice.auth.service;

import com.yundi.pollauthservice.auth.jwt.JwtUtil;
import com.yundi.pollauthservice.userauth.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        if (!jwtUtil.isValidToken(token)) {
            request.setAttribute("jwt", "Not valid jwt");
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtUtil.extractUsername(token);
        if (username == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            request.setAttribute("jwt", "Not valid jwt");
            filterChain.doFilter(request, response);
            return;
        }

        findUserAndSetAuthentication(username);
        filterChain.doFilter(request, response);
    }

    private void findUserAndSetAuthentication(String username) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
