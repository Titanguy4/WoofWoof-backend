FROM quay.io/keycloak/keycloak:26.4

ENV KC_HEALTH_ENABLED=true
ENV KC_METRICS_ENABLED=true

EXPOSE 8080

ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start", "--optimized", "--http-port=8080", "--hostname-strict=false"]
