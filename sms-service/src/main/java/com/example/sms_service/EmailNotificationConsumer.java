import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationConsumer {

    @RabbitListener(queues = "emailQueue")
    public void receiveEmailMessage(String message) {
        System.out.println("Received Email Notification: " + message);
    }
}