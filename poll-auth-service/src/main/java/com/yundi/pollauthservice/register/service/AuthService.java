package com.yundi.pollauthservice.register.service;

import com.yundi.pollauthservice.auth.jwt.JwtUtil;
import com.yundi.pollauthservice.register.dto.AuthenticationRequest;
import com.yundi.pollauthservice.register.dto.AuthenticationResponse;
import com.yundi.pollauthservice.register.dto.RegisterRequest;
import com.yundi.pollauthservice.userauth.model.UserAuth;
import com.yundi.pollauthservice.userauth.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserAuthService userAuthService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse getAccessTokenByRegister(RegisterRequest registerRequest) {
        userAuthService.saveAuth(UserAuth.builder()
                .username(registerRequest.getUsername())
                .password(registerRequest.getPassword())
                .build());
        //TODO SEND EVENT HERE
        return createTokens(registerRequest.getUsername());
    }

    public AuthenticationResponse getAccessTokenByCredentials(AuthenticationRequest request) {
        authenticateUser(request.getUsername(), request.getPassword());
        return createTokens(request.getUsername());
    }

    public AuthenticationResponse getAccessTokenByRefresh(HttpServletRequest request) {
        String refreshToken = getRefreshToken(request);
        String accessToken = jwtUtil.createAccessTokenByRefreshToken(refreshToken);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private AuthenticationResponse createTokens(String username) {
        String accessToken = jwtUtil.createToken(username, "access_token");
        String refreshToken = jwtUtil.createToken(username, "refresh_token");
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private String getRefreshToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization.isEmpty())
            throw new RuntimeException("Refresh Token is missing");

        return authorization.substring("Bearer ".length());
    }

    private void authenticateUser(String username, String password) {
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (Exception ex) {
            throw new RuntimeException("Bad Credentials");
        }
    }
}
