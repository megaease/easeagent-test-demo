package com.megaease.template.server.config;

import com.megaease.template.server.interfaces.api.v1.AuthRestController;
import com.megaease.template.server.security.filter.AuthenticationFailureHandlerImpl;
import com.megaease.template.server.security.filter.AuthenticationSuccessHandlerImpl;
import com.megaease.template.server.security.filter.JwtAuthenticationFilter;
import com.megaease.template.server.security.properties.SecurityProperties;
import com.megaease.template.server.security.service.AuthorizationManager;
import com.megaease.template.server.security.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

enum Encoder {
    /**
     * @see PasswordEncoder
     */
    BCRYPT("bcrypt"),
    PBKDF2("pbkdf2"),
    SCRYPT("scrypt"),
    SHA256("sha256"),
    MD5("md5");
    private String code;

    Encoder(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableConfigurationProperties(SecurityProperties.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthorizationManager authorizationManager;
    @Autowired
    private SecurityProperties properties;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        String idForEncode = Encoder.MD5.getCode();
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(Encoder.BCRYPT.getCode(), new BCryptPasswordEncoder());
        encoders.put(Encoder.PBKDF2.getCode(), new Pbkdf2PasswordEncoder());
        encoders.put(Encoder.SCRYPT.getCode(), new SCryptPasswordEncoder());
        encoders.put(Encoder.SHA256.getCode(), new StandardPasswordEncoder());
        encoders.put(Encoder.MD5.getCode(), new MessageDigestPasswordEncoder(Encoder.MD5.getCode()));
        return new DelegatingPasswordEncoder(idForEncode, encoders);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().logout().disable()
                .addFilterBefore(jwtAuthenticationFilter(), AnonymousAuthenticationFilter.class).anonymous()
                .and()
                .csrf().disable()
                .antMatcher("/**").authorizeRequests().anyRequest().authenticated()
        ;
    }

    /**
     * all public api must write here
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                "/v1/platform/auth"
        );
    }

    @Bean
    public JwtUtils jwtUtils() {
        JwtUtils utils = new JwtUtils();
        utils.setProperties(properties);
        return utils;
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationManager.class)
    public AuthenticationManager providerManager(List<AuthenticationProvider> providers) {
        return new ProviderManager(providers);
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandlerImpl(jwtUtils(), authorizationManager);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthenticationFailureHandlerImpl();
    }

    /**
     * This is a standard spring security login filter implementation,
     * but unfortunately, it can't be detected by Swagger smoothly,
     * I can only deprecated it.
     * use {@link AuthRestController} instead of this
     *
     * @param authenticationManager
     * @return
     */
    /*@Bean
    @Deprecated
    public PlatformLoginAuthenticationFilter platformAuthenticationFilter(AuthenticationManager authenticationManager) {
        final PlatformLoginAuthenticationFilter filter = new PlatformLoginAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager);
        AntPathRequestMatcher matcher = new AntPathRequestMatcher(properties.getPlatformAuthUrl(), HttpMethod.POST.name());
        filter.setRequiresAuthenticationRequestMatcher(matcher);
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(authenticationFailureHandler());
        return filter;
    }*/
}
