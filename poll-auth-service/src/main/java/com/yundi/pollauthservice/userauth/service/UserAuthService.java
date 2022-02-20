package com.yundi.pollauthservice.userauth.service;

import com.yundi.pollauthservice.userauth.model.UserAuth;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface UserAuthService {

    void saveAuth(UserAuth userAuth);

    UserDetails findUserDetailsByUsername(String username);
}
