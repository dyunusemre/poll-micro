package com.yundi.polluserservice.user.service;

import com.yundi.polluserservice.domain.model.User;
import com.yundi.polluserservice.domain.repository.UserRepository;
import com.yundi.polluserservice.user.dto.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void update(User user) {

    }

    public void updateEmail(String username, String newEmail) {
        userRepository.findByUsername(username).ifPresent(user -> {
            user.setEmail(newEmail);
            userRepository.save(user);
        });
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public UserProfileResponse getUserProfileInformation(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return getUserProfile(user);
    }

    private UserProfileResponse getUserProfile(User user) {
        return UserProfileResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .build();
    }
}
