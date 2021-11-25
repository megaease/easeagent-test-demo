package com.megaease.template.server.security.entity;

import com.megaease.template.server.enums.ClientId;
import com.megaease.template.server.enums.Realm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class RequestAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private long userId;
    private Realm realm;
    private ClientId clientId;

    public RequestAuthenticationToken(Object principal, Object credentials, final long userId, Realm realm, ClientId clientId) {
        super(principal, credentials);
        this.userId = userId;
        this.realm = realm;
        this.clientId = clientId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Realm getRealm() {
        return realm;
    }

    public void setRealm(Realm realm) {
        this.realm = realm;
    }

    public ClientId getClientId() {
        return clientId;
    }

    public void setClientId(ClientId clientId) {
        this.clientId = clientId;
    }
}
