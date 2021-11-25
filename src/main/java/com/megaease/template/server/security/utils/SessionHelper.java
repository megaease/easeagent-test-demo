package com.megaease.template.server.security.utils;

import com.megaease.template.server.security.entity.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SessionHelper {

    public static UserDetailsImpl getCurrentLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetailsImpl) authentication.getDetails();
    }

    public static Long getCurrentLoginUserId() {
        return getCurrentLoginUser().getId();
    }
}
