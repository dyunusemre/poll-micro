package com.yundi.pollauthservice.auth.service;

import com.yundi.pollauthservice.security.jwt.JwtUtil;
import com.yundi.pollauthservice.auth.dto.AuthenticationRequest;
import com.yundi.pollauthservice.auth.dto.AuthenticationResponse;
import com.yundi.pollauthservice.auth.dto.RegisterRequest;
import com.yundi.pollauthservice.userauth.model.UserAuth;
import com.yundi.pollauthservice.userauth.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserAuthService userAuthService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse getAccessTokenByRegister(RegisterRequest registerRequest) {
        UserAuth userAuth = userAuthService.saveAuth(UserAuth.builder()
                .username(registerRequest.getUsername())
                .password(registerRequest.getPassword())
                .build());

        UserDetails userDetails = userAuthService.findUserDetailsByUsername(userAuth.getUsername());
        return createTokens(userDetails);
    }

    public AuthenticationResponse getAccessTokenByCredentials(AuthenticationRequest request) {
        Authentication authentication = authenticateUser(request.getUsername(), request.getPassword());
        return createTokens((UserDetails) authentication.getPrincipal());
    }

    public AuthenticationResponse getAccessTokenByRefresh(HttpServletRequest request) {
        String refreshToken = getRefreshToken(request);
        String accessToken = jwtUtil.createAccessTokenByRefreshToken(refreshToken);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private AuthenticationResponse createTokens(UserDetails userDetails) {
        String accessToken = jwtUtil.createTokenByUserDetailsAndTokenType(userDetails, "access_token");
        String refreshToken = jwtUtil.createTokenByUserDetailsAndTokenType(userDetails, "refresh_token");
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

    private Authentication authenticateUser(String username, String password) {
        try {
            UserDetails userDetails = userAuthService.findUserDetailsByUsername(username);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword());
            return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (Exception ex) {
            throw new RuntimeException("Bad Credentials");
        }
    }
}
