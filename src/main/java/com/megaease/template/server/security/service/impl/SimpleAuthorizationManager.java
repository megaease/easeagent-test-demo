package com.megaease.template.server.security.service.impl;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@AllArgsConstructor
class Secret {
    private String secret;
    private long expiredTime;
}

public class SimpleAuthorizationManager extends AbstractAuthorizationManager {
    private Map<String, Secret> map = new ConcurrentHashMap<>();

    @Override
    public void loginInner(String realm, String clientId, long userId, String secret) {
        Secret secretItem = new Secret(secret, System.currentTimeMillis() + properties.getDefaultTimeout());
        map.put(generateKey(realm, clientId, userId), secretItem);
    }

    @Override
    public void logout(String realm, String clientId, long userId, String secret) {
        map.remove(generateKey(realm, clientId, userId));
    }

    private String getSecret(String realm, String clientId, long userId) {
        String key = generateKey(realm, clientId, userId);
        if (!map.containsKey(key)) {
            return null;
        }
        Secret secret = map.get(key);
        if (System.currentTimeMillis() > secret.getExpiredTime()) {
            map.remove(key);
            return null;
        }
        secret.setExpiredTime(System.currentTimeMillis() + properties.getDefaultTimeout());
        map.put(key, secret);
        return secret.getSecret();
    }

    @Override
    public boolean validateInner(String realm, String clinetId, long userId, String secret) {
        return secret != null && secret.equals(getSecret(realm, clinetId, userId));
    }
}
