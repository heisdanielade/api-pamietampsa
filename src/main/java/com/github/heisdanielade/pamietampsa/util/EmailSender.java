package com.github.heisdanielade.pamietampsa.util;

import com.github.heisdanielade.pamietampsa.entity.AppUser;
import com.github.heisdanielade.pamietampsa.service.EmailService;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class EmailSenders {

    private EmailService emailService;

    public void sendVerificationEmail(AppUser user, String subject){
        try {
            String htmlMessage = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/email/account/registration.html")));
            emailService.sendEmail(user.getEmail(), subject, htmlMessage);
        } catch (IOException e){
            System.out.println("(e) Error loading email template: " + e.getMessage());
        } catch (MessagingException e) {
            System.out.println("(e) Error sending email: " + e.getMessage());
        }
    }
}
