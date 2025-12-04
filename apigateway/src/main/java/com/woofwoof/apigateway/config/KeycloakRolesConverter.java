package com.woofwoof.apigateway.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * Permit to override the spring converteur
 * To map the keycloak roles from the jwt to the GrantedAuthority of Spring
 * Security
 */
public class KeycloakRolesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private final String CLAIM_REALM_ACCESS = "realm_access";
    private final String CLAIM_ROLES = "roles";
    private final String PREFIX_ROLE = "ROLE_";

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        Map<String, Object> realmAccess = jwt.getClaim(CLAIM_REALM_ACCESS);

        if (realmAccess != null && realmAccess.containsKey(CLAIM_ROLES)) {
            Object rolesObj = realmAccess.get(CLAIM_ROLES);

            if (rolesObj instanceof Collection<?> roles) {
                grantedAuthorities = roles.stream()
                        .filter(String.class::isInstance)
                        .map(role -> new SimpleGrantedAuthority(PREFIX_ROLE + role.toString().toUpperCase()))
                        .collect(Collectors.toList());
            }
        }

        return grantedAuthorities;
    }
}
