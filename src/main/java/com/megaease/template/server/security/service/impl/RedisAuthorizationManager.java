package com.megaease.template.server.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisAuthorizationManager extends AbstractAuthorizationManager {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void loginInner(String realm, String clinetId, long userId, String secret) {
        String key = generateKey(realm, clinetId, userId);
        redisTemplate.opsForValue().set(key, secret, properties.getDefaultTimeout(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void logout(String realm, String clinetId, long userId, String secret) {
        redisTemplate.delete(generateKey(realm, clinetId, userId));
    }

    private String getSecret(String realm, String clinetId, long userId) {
        String key = generateKey(realm, clinetId, userId);
        String secret = redisTemplate.opsForValue().get(key);
        redisTemplate.expire(key, properties.getDefaultTimeout(), TimeUnit.MILLISECONDS);
        return secret;
    }

    @Override
    public boolean validateInner(String realm, String clinetId, long userId, String secret) {
        return secret != null && secret.equals(getSecret(realm, clinetId, userId));
    }
}
