package com.internship.infosys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // ==========================================
    // Generic Email
    // ==========================================

    @Override
    public void sendEmail(
            String to,
            String subject,
            String body) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);

        message.setSubject(subject);

        message.setText(body);

        mailSender.send(message);
    }

    // ==========================================
    // Verification Email
    // ==========================================

    @Override
    public void sendVerificationEmail(
            String to,
            String username,
            String verificationLink) {

        String subject = "Verify Your Cloud Security Monitoring System Account";

        String body = """
                Hello %s,

                Welcome to Cloud Security Monitoring System.

                Thank you for registering.

                Please click the link below to verify your account.

                %s

                This verification link will expire in 24 hours.

                If you did not create this account,
                please ignore this email.

                Regards,
                Cloud Security Monitoring System  Team
                """
                .formatted(username, verificationLink);

        sendEmail(to, subject, body);
    }
}
