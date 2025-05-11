package com.github.heisdanielade.pamietampsa.util;

import com.github.heisdanielade.pamietampsa.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@Component
public class EmailSender {

    private final EmailService emailService;

    public EmailSender(EmailService emailService){
        this.emailService = emailService;
    }

    private void sendTemplatedEmail(String to, String subject, String templatePath, Map<String, String> placeholders) {
        try {
            String htmlMessage = new String(Files.readAllBytes(Paths.get(templatePath)));
            if (placeholders != null) {
                for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                    htmlMessage = htmlMessage.replace(entry.getKey(), entry.getValue());
                }
            }
            emailService.sendEmail(to, subject, htmlMessage);
        } catch (IOException e) {
            System.out.println("(e) Error loading email template: " + e.getMessage());
        } catch (MessagingException e) {
            System.out.println("(e) Error sending email: " + e.getMessage());
        }
    }


    // User-related emails
    public void sendVerificationEmail(String userEmail, String verificationCode){
        String subject = verificationCode + " is your verification code";
        Map<String, String> placeholders = Map.of("{{verification_code}}", verificationCode);
        sendTemplatedEmail(userEmail, subject, "src/main/resources/templates/email/auth/email-verification.html", placeholders);
    }
    public void sendUserRegistrationConfirmationEmail(String userEmail){
        String subject = "Welcome to PamietamPsa";
        sendTemplatedEmail(userEmail, subject, "src/main/resources/templates/email/account/user-registration.html", null);
    }

    // Pet-related emails
    public void sendPetRegistrationConfirmationEmail(String userEmail, String petName, String petSpecies){
        String subject = "New Pet registration (" + petName + ")";
        Map<String, String> placeholders = Map.of("{{petName}}", petName, "{{petSpecies}}", petSpecies);
        sendTemplatedEmail(userEmail, subject, "src/main/resources/templates/email/pet/pet-registration.html", placeholders);
    }



}
