package com.megaease.template.server.security.utils;

import com.megaease.template.server.enums.ClientId;
import com.megaease.template.server.enums.Realm;
import com.megaease.template.server.security.entity.UserDetailsImpl;
import com.megaease.template.server.security.properties.SecurityProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Key;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtUtils implements InitializingBean {

    public static final String KEY_USER_ID = "userId";
    public static final String KEY_REALM = "realm";
    public static final String KEY_CLIENT_ID = "clientId";
    public static final String KEY_RAND = "randNum";
    private SecurityProperties properties;
    private Key key;

    @Autowired
    public void setProperties(SecurityProperties securityProperties) {
        this.properties = securityProperties;
    }

    public String createUserToken(UserDetailsImpl userDetails, String secret) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put(KEY_USER_ID, userDetails.getId());
        dataMap.put(KEY_REALM, userDetails.getRealm().name());
        dataMap.put(KEY_CLIENT_ID, userDetails.getClientId().name());
        dataMap.put(KEY_RAND, secret);
        return createToken(dataMap);
    }

    public UserDetailsImpl parseUser(String token) {
        try {
            Map<String, Object> map = parseToken(token);
            return UserDetailsImpl.builder()
                    .id(Long.valueOf(map.get(KEY_USER_ID).toString()))
                    .username("")
                    .password("")
                    .realm(Realm.valueOf(map.get(KEY_REALM).toString()))
                    .clientId(ClientId.valueOf(map.get(KEY_CLIENT_ID).toString()))
                    .build();
        } catch (NullPointerException e) {
            if (log.isDebugEnabled()) {
                log.error("token validation failed with missing required fields");
            }
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.error("parse token fail, {}", e);
            }
        }
        return null;
    }

    public UserDetailsImpl parseUser(Map<String, Object> map) {
        try {
            return UserDetailsImpl.builder()
                    .id(Long.valueOf(map.get(KEY_USER_ID).toString()))
                    .username("")
                    .password("")
                    .realm(Realm.valueOf(map.get(KEY_REALM).toString()))
                    .clientId(ClientId.valueOf(map.get(KEY_CLIENT_ID).toString()))
                    .build();
        } catch (NullPointerException e) {
            if (log.isDebugEnabled()) {
                log.error("token validation failed with missing required fields");
            }
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.error("parse token fail, {}", e);
            }
        }
        return null;
    }

    String createToken(Map<String, Object> dataMap) {
        return Jwts.builder().setClaims(dataMap).signWith(key).compact();
    }

    public Map<String, Object> parseToken(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }

    @Override
    public void afterPropertiesSet() {
        byte[] bytes = Base64.getDecoder().decode(properties.getSecretKey());
        this.key = Keys.hmacShaKeyFor(bytes);
    }
}
