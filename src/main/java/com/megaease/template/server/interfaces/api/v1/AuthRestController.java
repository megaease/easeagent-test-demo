package com.megaease.template.server.interfaces.api.v1;

import com.megaease.template.server.constants.ApiPrefix;
import com.megaease.template.server.dto.PlatformLoginCommand;
import com.megaease.template.server.enums.ClientId;
import com.megaease.template.server.enums.Realm;
import com.megaease.template.server.security.entity.LoginAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping(value = ApiPrefix.V1_BASE_PREFIX)
@Slf4j
public class AuthRestController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthenticationSuccessHandler successHandler;
    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    @PostMapping("/platform/auth")
    public void auth(@RequestBody @Valid PlatformLoginCommand command) throws IOException, ServletException {
        String username = command.getUsername();
        String password = command.getPassword();
        ClientId clientId = command.getClientId() == null ? ClientId.BROWSER : command.getClientId();
        auth(username, password, Realm.PLATFORM, clientId);
    }


    private void auth(String username, String password, Realm realm, ClientId clientId) throws IOException, ServletException {
        LoginAuthenticationToken token = new LoginAuthenticationToken(username, password, realm, clientId);
        try {
            Authentication authentication = authenticationManager.authenticate(token);
            successHandler.onAuthenticationSuccess(request, response, authentication);
        } catch (AuthenticationException e) {
            failureHandler.onAuthenticationFailure(request, response, e);
        } catch (Exception e) {
            log.error("username[{}] login fail, reason: {}", username, e);
            response.sendError(HttpStatus.UNAUTHORIZED.value());
        }

    }
}
