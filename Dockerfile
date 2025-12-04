FROM quay.io/keycloak/keycloak:20.0.0

ENV KC_HEALTH_ENABLED=true
ENV KC_METRICS_ENABLED=true

ENV KC_HOSTNAME=auth.hugotanguy.fr
ENV KC_HOSTNAME_ADMIN=keycloak.hugotanguy.fr
ENV KC_PROXY=edge
ENV KC_HTTP_ENABLED=true
ENV KC_HOSTNAME_STRICT_HTTPS=false

ENV KC_DB=postgres
ENV KC_DB_URL=jdbc:postgresql://keycloak-pg:5432/keycloak
ENV KC_DB_USERNAME=postgres
ENV KC_DB_PASSWORD=5w84{a2HLnUrF#

ENV KEYCLOAK_ADMIN=KEYCLOAKADMINHUGO
ENV KEYCLOAK_ADMIN_PASSWORD=4xmH7Gja5RrF783D

WORKDIR /opt/keycloak
RUN /opt/keycloak/bin/kc.sh build

ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start", "--optimized"]
