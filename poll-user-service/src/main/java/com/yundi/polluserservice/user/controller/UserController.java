package com.yundi.polluserservice.user.controller;

import com.yundi.polluserservice.common.BaseController;
import com.yundi.polluserservice.user.dto.UserProfileResponse;
import com.yundi.polluserservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserService userService;

    @PutMapping("/update-email")
    public void updateEmail(Authentication authentication, @RequestBody String email) {
        userService.updateEmail(getUsername(authentication), email);
    }

    @GetMapping("/information")
    public UserProfileResponse getUserInformation(Authentication authentication) {
        return userService.getUserProfileInformation(getUsername(authentication));
    }
}
