package com.woofwoof.monitoring.service;

import java.util.Map;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LogConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String GREEN = "\u001B[32m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";

    @KafkaListener(topics = "api-logs", groupId = "groupe-monitoring")
    public void consume(String message) {
        try {
            Map<String, Object> logMap = objectMapper.readValue(message, Map.class);

            String level = (String) logMap.get("level");
            String service = (String) logMap.get("service");
            String msg = (String) logMap.get("message");
            String timestamp = (String) logMap.get("timestamp");

            String color = switch (level) {
                case "ERROR" -> RED;
                case "WARN" -> YELLOW;
                case "INFO" -> GREEN;
                default -> BLUE;
            };

            String time = timestamp != null && timestamp.length() > 11 ? timestamp.substring(11, 19) : timestamp;

            String formattedLog = String.format("%s[%s] %s[%-15s] %s[%-5s]%s : %s",
                    CYAN, time,
                    BLUE, service,
                    color, level,
                    RESET, msg);

            log.info(formattedLog);

        } catch (Exception e) {
            log.error("{}Log brut reçu (erreur format): {}{}", RED, message, RESET);
        }
    }
}