package com.megaease.template.server.security.service;


public interface AuthorizationManager {

    void login(String realm, String clinetId, long userId, String secret);

    void logout(String realm, String clinetId, long userId, String secret);

    boolean validate(String realm, String clinetId, long userId, String secret);
}
