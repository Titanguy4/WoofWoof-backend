package com.woofwoof.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.DelegatingJwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.woofwoof.apigateway.service.KafkaLogService;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
        private String jwk;

        @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
        private String issuerUri;

        DelegatingJwtGrantedAuthoritiesConverter authoritiesConverter = new DelegatingJwtGrantedAuthoritiesConverter(
                        new JwtGrantedAuthoritiesConverter(),
                        new KeycloakRolesConverter());

        /**
         * Bean to configure the security of the routes
         * Here you can add your personalize routes with roles
         * 
         * @param http
         * @param kafkaLogService On injecte le service de log ici
         * @return
         * @throws Exception
         */
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http, KafkaLogService kafkaLogService) throws Exception {
                http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(authorize -> authorize
                                                // Pour l'instant je permit tous mais à vous d'ajouter vos endpoints
                                                // .requestMatchers("/stays/**").hasRole("BACKPACKER")
                                                .anyRequest().permitAll())
                                .oauth2ResourceServer(oauth2 -> oauth2
                                                .jwt(jwt -> jwt.decoder(jwtDecoder())
                                                                .jwtAuthenticationConverter(
                                                                                jwtAuthenticationConverter())))
                                .exceptionHandling(exceptions -> exceptions
                                                // Erreur 401 : Non authentifié
                                                .authenticationEntryPoint((request, response, authException) -> {
                                                        String path = request.getRequestURI();
                                                        kafkaLogService.sendLog("WARN", "Authentication Failed on "
                                                                        + path + " : " + authException.getMessage());
                                                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                                                                        authException.getMessage());
                                                })
                                                // Erreur 403 : Accès interdit (Rôle insuffisant)
                                                .accessDeniedHandler((request, response, accessDeniedException) -> {
                                                        String path = request.getRequestURI();
                                                        String user = request.getUserPrincipal() != null
                                                                        ? request.getUserPrincipal().getName()
                                                                        : "Anonymous";
                                                        kafkaLogService.sendLog("WARN", "Access Denied for user " + user
                                                                        + " on " + path);
                                                        response.sendError(HttpServletResponse.SC_FORBIDDEN,
                                                                        accessDeniedException.getMessage());
                                                }));

                return http.build();
        }

        /**
         * Add to the default converter of Spring
         * our Keycloak Converter
         * 
         * @return
         */
        @Bean
        public JwtAuthenticationConverter jwtAuthenticationConverter() {
                DelegatingJwtGrantedAuthoritiesConverter converters = new DelegatingJwtGrantedAuthoritiesConverter(
                                new JwtGrantedAuthoritiesConverter(),
                                new KeycloakRolesConverter());

                JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
                jwtConverter.setJwtGrantedAuthoritiesConverter(converters);
                return jwtConverter;
        }

        /**
         * Because we run keycloak in docker localy the issuer is != in expo and spring
         * so with bind jwk to the jwtDecoder to accept both
         * 
         * @return
         */
        @Bean
        public JwtDecoder jwtDecoder() {
                NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwk).build();
                OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuerUri);
                jwtDecoder.setJwtValidator(withIssuer);

                return jwtDecoder;
        }
}
