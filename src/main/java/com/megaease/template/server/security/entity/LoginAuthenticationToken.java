package com.megaease.template.server.security.entity;

import com.megaease.template.server.enums.ClientId;
import com.megaease.template.server.enums.Realm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private Realm realm;
    private ClientId clientId;

    public LoginAuthenticationToken(Object principal, Object credentials, Realm realm, ClientId clientId) {
        super(principal, credentials);
        this.realm = realm;
        this.clientId = clientId;
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
