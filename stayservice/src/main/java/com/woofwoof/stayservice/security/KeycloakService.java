package com.woofwoof.stayservice.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class KeycloakService {

    public String getUserId() {
        JwtAuthenticationToken auth =
            (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        return auth.getToken().getSubject();   // userId Keycloak
    }
}
