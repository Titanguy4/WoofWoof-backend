package com.woofwoof.apigateway.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

public class KeycloakRolesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private final String CLAIM_REALM_ACCESS = "realm_access";
    private final String PREFIX_ROLE = "ROLE_";

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        Map<String, Object> realmAccess = jwt.getClaims();

        if (realmAccess != null && realmAccess.containsKey(CLAIM_REALM_ACCESS)) {

            Collection<String> roles = (Collection<String>) realmAccess.get(CLAIM_REALM_ACCESS);

            if (roles != null && !roles.isEmpty() && !(roles instanceof Collection)) {
                Collection<GrantedAuthority> realmRoles = roles.stream()
                        .map(role -> new SimpleGrantedAuthority(PREFIX_ROLE + role))
                        .collect(Collectors.toList());
                grantedAuthorities.addAll(realmRoles);
            }
        }

        return grantedAuthorities;
    }

}
