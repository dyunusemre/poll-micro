package com.yundi.pollquestionservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        {
            String username = request.getHeader("username");
            String role = request.getHeader("role");
            if (!StringUtils.hasLength(username) || !StringUtils.hasLength(role)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not Authorized");
                filterChain.doFilter(request, response);
                return;
            }
            setAuthorization(username, role);
            filterChain.doFilter(request, response);
        }
    }

    private void setAuthorization(String username, String role) {
        Collection<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(role));
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
