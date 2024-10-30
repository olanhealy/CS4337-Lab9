import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class SMSNotificationConsumer {

    @RabbitListener(queues = "smsQueue")
    public void receiveEmailMessage(Notification message) {
        System.out.println("Received SMS Notification: " + message);
    }

}