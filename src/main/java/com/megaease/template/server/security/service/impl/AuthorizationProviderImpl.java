package com.megaease.template.server.security.service.impl;

import com.megaease.template.server.domain.PlatformUser;
import com.megaease.template.server.enums.Realm;
import com.megaease.template.server.security.entity.LoginAuthenticationToken;
import com.megaease.template.server.security.entity.UserDetailsImpl;
import com.megaease.template.server.service.PlatformUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationProviderImpl extends AbstractUserDetailsAuthenticationProvider {
    @Autowired
    private PlatformUserService platformUserService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected UserDetailsImpl retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) {
        LoginAuthenticationToken token = (LoginAuthenticationToken) authentication;
        String credentials = authentication.getCredentials().toString();

        switch (token.getRealm()) {
            case PLATFORM:
                PlatformUser user = platformUserService.findByUsernameNotNull(username);
                if (user == null) {
                    throw new UsernameNotFoundException("error.platform_user.no_exist");
                }
                if (!passwordEncoder.matches(credentials, user.getPassword())) {
                    throw new BadCredentialsException("error.platform_user.login.password");
                }
                return UserDetailsImpl.builder()
                        .id(user.getId())
                        .username(username)
                        .password("")
                        .realm(Realm.PLATFORM)
                        .clientId(((LoginAuthenticationToken) authentication).getClientId())
                        .build();
            default:
                return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return LoginAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) {

    }

    @Override
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
        LoginAuthenticationToken result = (LoginAuthenticationToken) authentication;
        result.setDetails(user);
        return result;
    }
}
