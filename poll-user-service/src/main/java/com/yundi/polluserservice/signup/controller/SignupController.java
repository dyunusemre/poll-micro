package com.yundi.polluserservice.signup.controller;

import com.yundi.polluserservice.recaptcha.RequiredRecaptcha;
import com.yundi.polluserservice.signup.dto.SignupRequest;
import com.yundi.polluserservice.signup.service.SignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class SignupController {

    private final SignupService signupService;

    @RequiredRecaptcha(isEnabled = false)
    @PostMapping("/sign-up")
    public void registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        signupService.register(signupRequest);
    }
}
