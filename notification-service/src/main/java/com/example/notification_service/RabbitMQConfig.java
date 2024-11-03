package com.example.notification_service;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;

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


//new code, queue for each service
	@Bean
	public Queue emailQueue() {
    	return new Queue("emailQueue", false);
	}

	@Bean
	public Queue smsQueue() {
       Map<String, Object> args = new HashMap<>();
       args.put("x-dead-letter-exchange", "deadLetterExchange");
       args.put("x-dead-letter-routing-key", "deadLetter.sms");
       return QueueBuilder.durable("smsQueue").withArguments(args).build();
   	}


	@Bean
	public Queue pushQueue() {
    	return new Queue("pushQueue", false);
	}

//binds each queue to its respective routing key
	@Bean
	public Binding emailBinding(Queue emailQueue, TopicExchange exchange) {
    	return BindingBuilder.bind(emailQueue).to(exchange).with("notification.email");
	}

	@Bean
	public Binding smsBinding(Queue smsQueue, TopicExchange exchange) {
    	return BindingBuilder.bind(smsQueue).to(exchange).with("notification.sms");
	}

	@Bean
	public Binding pushBinding(Queue pushQueue, TopicExchange exchange) {
    	return BindingBuilder.bind(pushQueue).to(exchange).with("notification.push");
	}
	//binding the fanout exchange
	@Bean
	public Binding bindEmailToFanout(FanoutExchange fanoutExchange, Queue emailQueue) {
		return BindingBuilder.bind(emailQueue).to(fanoutExchange);
	}

	@Bean
	public Binding bindPushToFanout(FanoutExchange fanoutExchange, Queue pushQueue) {
		return BindingBuilder.bind(pushQueue).to(fanoutExchange);
	}

	@Bean
	public Binding bindSmsToFanout(FanoutExchange fanoutExchange, Queue smsQueue) {
		return BindingBuilder.bind(smsQueue).to(fanoutExchange);
	}

	@Bean
	public Queue deadLetterQueue() {
		return QueueBuilder.durable("deadLetterQueue")
				.withArgument("x-message-ttl", 60000) // << TTL is set Here
				.build();
	}

	@Bean
	public Exchange deadLetterExchange() {
		return new TopicExchange("deadLetterExchange");
	}
	
	@Bean
	public Binding deadLetterBinding() {
		return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with("deadLetter.#").noargs();
	}
}