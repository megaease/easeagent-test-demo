package com.megaease.template.server.config;

import com.megaease.template.server.security.service.impl.AuthorizationProviderImpl;
import com.megaease.template.server.security.service.impl.UserDetailsAuthenticationProiderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;

@Configuration
public class GlobalAuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {
    @Autowired
    private AuthorizationProviderImpl authorizationProvider;
    @Autowired
    private UserDetailsAuthenticationProiderImpl userDetailsAuthenticationProider;


    @Override
    public void init(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .authenticationProvider(authorizationProvider)
                .authenticationProvider(userDetailsAuthenticationProider);
    }
}

