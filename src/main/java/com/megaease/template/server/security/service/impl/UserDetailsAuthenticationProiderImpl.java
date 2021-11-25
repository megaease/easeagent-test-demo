package com.megaease.template.server.security.service.impl;

import com.megaease.template.server.domain.PlatformUser;
import com.megaease.template.server.enums.Realm;
import com.megaease.template.server.security.entity.RequestAuthenticationToken;
import com.megaease.template.server.security.entity.UserDetailsImpl;
import com.megaease.template.server.service.PlatformUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsAuthenticationProiderImpl extends AbstractUserDetailsAuthenticationProvider {
    @Autowired
    private PlatformUserService platformUserService;

    @Override
    protected UserDetailsImpl retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) {
        RequestAuthenticationToken token = (RequestAuthenticationToken) authentication;
        switch (token.getRealm()) {
            case PLATFORM: {
                PlatformUser platformUser = platformUserService.findByIdNotNull(token.getUserId());
                return UserDetailsImpl.builder()
                        .realm(Realm.PLATFORM)
                        .clientId(token.getClientId())
                        .id(platformUser.getId())
                        .username(username)
                        .password(platformUser.getPassword())
                        .build();
            }
            default:
                return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return RequestAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) {

    }

    @Override
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
        RequestAuthenticationToken result = (RequestAuthenticationToken) authentication;
        result.setDetails(user);
        return result;
    }
}
