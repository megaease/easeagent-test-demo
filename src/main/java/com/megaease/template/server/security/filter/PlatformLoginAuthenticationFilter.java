package com.megaease.template.server.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.megaease.template.server.enums.ClientId;
import com.megaease.template.server.enums.Realm;
import com.megaease.template.server.security.entity.LoginAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * This is a standard spring security login filter implementation,
 * but unfortunately, it can't be detected by Swagger smoothly,
 * I can only deprecated it.
 */
@Deprecated
public class PlatformLoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            Map map = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            String username = map.getOrDefault("username", "").toString();
            String password = map.getOrDefault("password", "").toString();
            String clientId = map.getOrDefault("clientId", ClientId.BROWSER.name()).toString();
            LoginAuthenticationToken token = new LoginAuthenticationToken(
                    username,
                    password,
                    Realm.PLATFORM,
                    ClientId.valueOf(clientId)
            );
            return getAuthenticationManager().authenticate(token);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.getFailureHandler().onAuthenticationFailure(request, response, failed);
    }
}
