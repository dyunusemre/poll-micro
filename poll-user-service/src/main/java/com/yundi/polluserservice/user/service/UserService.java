package com.yundi.polluserservice.user.service;
import com.yundi.polluserservice.domain.model.User;
import com.yundi.polluserservice.user.dto.UserProfileResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User save(User user);

    void update(User user);

    void updateEmail(String username, String email);

    User findUserByUsername(String username);

    UserProfileResponse getUserProfileInformation(String username);
}
