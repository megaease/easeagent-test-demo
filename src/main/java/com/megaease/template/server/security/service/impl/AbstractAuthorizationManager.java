package com.megaease.template.server.security.service.impl;

import com.megaease.template.server.security.properties.SecurityProperties;
import com.megaease.template.server.security.service.AuthorizationManager;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractAuthorizationManager implements AuthorizationManager {
    protected static final String STORE_NAME = "login";
    @Autowired
    protected SecurityProperties properties;

    @Override
    public void login(String realm, String clinetId, long userId, String secret) {
        loginInner(realm, clinetId, userId, secret);
    }

    protected abstract void loginInner(String realm, String clinetId, long userId, String secret);

    @Override
    public boolean validate(String realm, String clinetId, long userId, String secret) {
        return validateInner(realm, clinetId, userId, secret);
    }

    protected abstract boolean validateInner(String realm, String clinetId, long userId, String secret);

    final String generateKey(String realm, String clientId, final long userId) {
        return String.format("%s:%s:%s:%s", STORE_NAME, realm, clientId, userId);
    }
}
