package com.example.email_service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationConsumer {

    @RabbitListener(queues = "emailQueue") // Specify the email queue here
    public void receiveEmailMessage(String message) {
        System.out.println("Received Email Notification: " + message);
        // Process the message, e.g., send an email
    }
}