package com.yundi.pollauthservice.userauth.service;

import com.yundi.pollauthservice.userauth.enums.RoleEnum;
import com.yundi.pollauthservice.userauth.model.Role;
import com.yundi.pollauthservice.userauth.model.UserAuth;
import com.yundi.pollauthservice.userauth.repository.RoleRepository;
import com.yundi.pollauthservice.userauth.repository.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private final PasswordEncoder encoder;
    private final UserAuthRepository userAuthRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserAuth saveAuth(UserAuth userAuth) {
        userAuth.setRoles(findRoleByName(RoleEnum.ROLE_USER));
        userAuth.setPassword(encoder.encode(userAuth.getPassword()));

        if (isUsernameUnique(userAuth.getUsername()))
            throw new RuntimeException("Username is exists");

        return userAuthRepository.save(userAuth);
    }

    @Override
    public UserDetails findUserDetailsByUsername(String username) {
        UserAuth userAuth = userAuthRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new org.springframework.security.core.userdetails.User(userAuth.getUsername(), userAuth.getPassword(), getAuthorities(userAuth));
    }

    @Override
    public Set<Role> findRoleByName(RoleEnum name) {
        return Collections.singleton(roleRepository.findByName(name).orElseThrow(() -> new RuntimeException("Role Couldnt Fine")));
    }

    private Set<GrantedAuthority> getAuthorities(UserAuth user) {
        return user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toSet());
    }

    private boolean isUsernameUnique(String username) {
        return userAuthRepository.findByUsername(username).isPresent();
    }
}
