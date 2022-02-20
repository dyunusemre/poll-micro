package com.yundi.pollauthservice.userauth.service;

import com.yundi.pollauthservice.userauth.model.UserAuth;
import com.yundi.pollauthservice.userauth.repository.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private final PasswordEncoder encoder;
    private final UserAuthRepository userAuthRepository;

    @Override
    public void saveAuth(UserAuth userAuth) {
        userAuth.setPassword(encoder.encode(userAuth.getPassword()));
        if (isUsernameUnique(userAuth.getUsername()))
            throw new RuntimeException("Username is exists");

        userAuthRepository.save(userAuth);
    }

    @Override
    public UserDetails findUserDetailsByUsername(String username) {
        UserAuth user = userAuthRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        //TODO Set grants here
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.emptyList());
    }

    private boolean isUsernameUnique(String username) {
        return userAuthRepository.findByUsername(username).isPresent();
    }
}
