package io.reflectoring.AddressBookApp.Controllers;

import io.reflectoring.AddressBookApp.Services.RabbitMQProducer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/email")
@Tag(name = "Email API", description = "Endpoints for sending emails via RabbitMQ")
public class EmailController {

    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    @PostMapping("/send")
    @Operation(summary = "Send an email", description = "Publishes an email request to RabbitMQ queue.")
    public ResponseEntity<String> sendEmail(@RequestBody(required = false) Map<String, String> emailData) {
        if (emailData == null || emailData.get("to") == null || emailData.get("subject") == null || emailData.get("message") == null) {
            return ResponseEntity.badRequest().body("Missing required fields: 'to', 'subject', or 'message'");
        }

        try {
            rabbitMQProducer.sendMessage(emailData);
            return ResponseEntity.ok("Email request sent to RabbitMQ queue successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email request: " + e.getMessage());
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected Error: " + e.getMessage());
    }
}
