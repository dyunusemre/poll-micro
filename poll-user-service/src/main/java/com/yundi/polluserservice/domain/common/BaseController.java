package com.yundi.polluserservice.domain.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class BaseController {
    protected String getUsername(Authentication authentication) {
        if (authentication == null) return null;
        return ((UserDetails) authentication.getPrincipal()).getUsername();
    }
}
