package com.megaease.template.server.config;


import com.megaease.template.server.security.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * this is a cors config for develop and test environment for web front
 */
@Configuration
public class WebCorsConfig {
    @Autowired
    private SecurityProperties properties;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        String[] allowedOrigins = new String[properties.getAllowedOrigins().size()];
        properties.getAllowedOrigins().toArray(allowedOrigins);
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .maxAge(3600)
                        .allowedOrigins(allowedOrigins)
                        .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                        .allowedHeaders("Origin", "X-Requested-With", "Content-Type", "Accept", "Authorization")
                        .exposedHeaders("Location", "Authorization");
            }
        };
    }
}