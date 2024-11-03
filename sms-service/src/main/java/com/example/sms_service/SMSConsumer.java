package com.example.sms_service;


import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SmsConsumer {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "smsQueue", ackMode = "MANUAL")
    public void receiveSmsMessage(String message, Channel channel, Message amqpMessage) {
        try {
            System.out.println("Received SMS Notification: " + message);
            if (message.contains("ERROR TEST")) {
                throw new RuntimeException("Testing error");
            }
            //acknowledge success
            channel.basicAck(amqpMessage.getMessageProperties().getDeliveryTag(), false);
            System.out.println("Message processed successfully.");
        } catch (Exception e) {
            handleFailure(channel, amqpMessage);
        }
    }

    private void handleFailure(Channel channel, Message amqpMessage) {
        try {
            System.out.println("Sending message to DLX due to processing error.");
            rabbitTemplate.convertAndSend("deadLetterExchange", "deadLetter.sms", amqpMessage);
            // acknowledge  message & remove from queue
            channel.basicAck(amqpMessage.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception ex) {
            System.out.println("Failed to send to DLX: " + ex.getMessage());
        }
    }
}
