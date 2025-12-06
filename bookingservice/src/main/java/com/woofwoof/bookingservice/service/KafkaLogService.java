package com.woofwoof.bookingservice.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaLogService {
    private final Logger logger = Logger.getLogger(KafkaLogService.class.getName());

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    private static final String TOPIC = "api-logs";
    private static final String SERVICE_NAME = "booking-service";

    public KafkaLogService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendLog(String level, String message) {
        try {
            Map<String, Object> logMap = new HashMap<>();

            logMap.put("service", SERVICE_NAME);
            logMap.put("level", level);
            logMap.put("message", message);
            logMap.put("timestamp", LocalDateTime.now().toString());

            String jsonLog = objectMapper.writeValueAsString(logMap);

            kafkaTemplate.send(TOPIC, SERVICE_NAME, jsonLog);
        } catch (JsonProcessingException e) {
            logger.debug("Erreur lors de la désérialisation du message pour envoyer à Kafka", e);
        }
    }
}
