package com.megaease.template.server.security.filter;

import com.megaease.template.server.security.constants.SecurityContants;
import com.megaease.template.server.security.entity.UserDetailsImpl;
import com.megaease.template.server.security.service.AuthorizationManager;
import com.megaease.template.server.security.utils.JwtUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    private final JwtUtils jwtUtils;
    private final AuthorizationManager authorizationManager;

    public AuthenticationSuccessHandlerImpl(JwtUtils jwtUtils, AuthorizationManager authorizationManager) {
        this.jwtUtils = jwtUtils;
        this.authorizationManager = authorizationManager;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetailsImpl user = (UserDetailsImpl) authentication.getDetails();
        String secret = UUID.randomUUID().toString();
        String jwtToken = jwtUtils.createUserToken(user, secret);
        authorizationManager.login(user.getRealm().name(), user.getClientId().name(), user.getId(), secret);
        response.getWriter().write(SecurityContants.TOKEN_PREFIX + jwtToken);
    }
}
