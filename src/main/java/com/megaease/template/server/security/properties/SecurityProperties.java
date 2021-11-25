package com.megaease.template.server.security.properties;

import com.megaease.template.server.constants.ApiPrefix;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "template.security")
public class SecurityProperties {
    private List<String> allowedOrigins;
    private long defaultTimeout = 1000 * 60 * 60 * 2;
    private String secretKey;
    private String platformAuthUrl = ApiPrefix.PLATFORM_PREFIX + "/public/auth";

    public List<String> getAllowedOrigins() {
        return allowedOrigins;
    }

    public void setAllowedOrigins(List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    public long getDefaultTimeout() {
        return defaultTimeout;
    }

    public void setDefaultTimeout(long defaultTimeout) {
        this.defaultTimeout = defaultTimeout;
    }

    public String getPlatformAuthUrl() {
        return platformAuthUrl;
    }

    public void setPlatformAuthUrl(String platformAuthUrl) {
        this.platformAuthUrl = platformAuthUrl;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
