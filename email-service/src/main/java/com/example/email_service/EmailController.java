package com.example.email_service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EmailController {
    private final EmailProducer producer;

    public EmailController(EmailProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestBody EmailMessage message, @RequestParam String exchangeType) {
        producer.sendEmail(message, exchangeType);
        return ResponseEntity.ok("Email sent successfully");
    }
}