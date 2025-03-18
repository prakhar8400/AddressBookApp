package io.reflectoring.AddressBookApp.Controllers;

import io.reflectoring.AddressBookApp.Services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.Map;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody(required = false) Map<String, String> emailData) {
        if (emailData == null || !emailData.containsKey("to") || !emailData.containsKey("subject") || !emailData.containsKey("message")) {
            return ResponseEntity.badRequest().body("Missing required fields: 'to', 'subject', or 'message'");
        }

        String to = emailData.get("to");
        String subject = emailData.get("subject");
        String message = emailData.get("message");

        emailService.sendEmail(to, subject, message);
        return ResponseEntity.ok("Email sent successfully to " + to);
    }

    // Handle missing request parameters globally
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
    }
}
