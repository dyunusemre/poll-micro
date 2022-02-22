package com.yundi.pollauthservice.auth.controller;

import com.yundi.pollauthservice.auth.dto.AuthenticationRequest;
import com.yundi.pollauthservice.auth.dto.AuthenticationResponse;
import com.yundi.pollauthservice.auth.dto.RegisterRequest;
import com.yundi.pollauthservice.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthenticationResponse login(@Valid @RequestBody AuthenticationRequest request) throws Exception {
        return authService.getAccessTokenByCredentials(request);
    }

    @PostMapping("/refresh")
    public AuthenticationResponse refresh(HttpServletRequest request) throws Exception {
        return authService.getAccessTokenByRefresh(request);
    }

    @PostMapping("/sign-up")
    public AuthenticationResponse signUp(@RequestBody RegisterRequest registerRequest) throws Exception {
        return authService.getAccessTokenByRegisterAndSendUser(registerRequest);
    }
}
