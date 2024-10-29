package com.example.notificationservice;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Exchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    @Bean
    public Exchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }
}