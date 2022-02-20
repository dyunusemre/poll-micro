package com.yundi.polluserservice.signup.service;

import com.yundi.polluserservice.domain.model.User;
import com.yundi.polluserservice.signup.dto.SignupRequest;
import com.yundi.polluserservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignupService {
    private final UserService userService;

    public void register(SignupRequest signupRequest) {
        validateNewUser();
        User user = userService.save(userBuilder(signupRequest));
    }

    private void validateNewUser() {
        //TODO
    }

    private User userBuilder(SignupRequest signupRequest) {
        return User.builder()
                .username(signupRequest.getUsername())
                .email(signupRequest.getEmail())
                .name(signupRequest.getName())
                .lastName(signupRequest.getLastName())
                .password(signupRequest.getPassword())
                .build();
    }
}
