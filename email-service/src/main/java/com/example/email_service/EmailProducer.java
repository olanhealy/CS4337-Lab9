package com.example.email_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailProducer {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public EmailProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendEmail(NotificationMessage message, String exchangeType) {
        String exchange = exchangeType.equalsIgnoreCase("fanout") ? "fanoutExchange" : "topicExchange";
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            String routingKey = exchangeType.equalsIgnoreCase("topic") ? message.getRoutingKey() : "";
            rabbitTemplate.convertAndSend(exchange, routingKey, jsonMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();

        }
    }
}