FROM quay.io/keycloak/keycloak:21.1.2-legacy

ENV KC_HEALTH_ENABLED=true
ENV KC_METRICS_ENABLED=true

EXPOSE 8080

ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start", "--optimized", "--http-port=8080", "--hostname-strict=false"]
