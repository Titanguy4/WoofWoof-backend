# Dockerfile for deploy keycloak but doesn't work in my serv
FROM quay.io/keycloak/keycloak:20.0.0

RUN echo "Build at $(date)" > /tmp/buildtime

ENV KC_HEALTH_ENABLED=true
ENV KC_METRICS_ENABLED=true

EXPOSE 8080

ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start", "--optimized", "--http-port=8080", "--hostname-strict=false", "http-enabled=true"]
