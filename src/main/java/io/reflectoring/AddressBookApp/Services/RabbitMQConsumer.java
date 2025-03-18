package io.reflectoring.AddressBookApp.Services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class RabbitMQConsumer {

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = "emailQueue")
    public void receiveMessage(Map<String, String> emailData) {
        String to = emailData.get("to");
        String subject = emailData.get("subject");
        String message = emailData.get("message");

        emailService.sendEmail(to, subject, message);
        System.out.println("Email sent to: " + to);
    }
}

