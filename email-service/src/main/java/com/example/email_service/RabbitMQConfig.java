import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue emailQueue() { // For Email Service
        return new Queue("emailQueue", false);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    @Bean
    public Binding binding(Queue emailQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(emailQueue).to(topicExchange).with("notification.email");
    }
}
}