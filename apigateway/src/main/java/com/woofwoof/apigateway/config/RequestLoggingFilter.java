package com.woofwoof.apigateway.config;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.woofwoof.apigateway.service.KafkaLogService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    private final KafkaLogService kafkaLogService;

    public RequestLoggingFilter(KafkaLogService kafkaLogService) {
        this.kafkaLogService = kafkaLogService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        long startTime = System.currentTimeMillis();
        String path = request.getRequestURI();
        String method = request.getMethod();

        try {
            filterChain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            int status = response.getStatus();

            String message = String.format("Method: %s | Path: %s | Status: %d | Duration: %dms",
                    method, path, status, duration);

            if (status >= 400) {
                kafkaLogService.sendLog("ERROR", message);
            } else {
                kafkaLogService.sendLog("INFO", message);
            }
        }
    }
}
