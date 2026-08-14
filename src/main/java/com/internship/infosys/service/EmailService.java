package com.internship.infosys.service;

public interface EmailService {

    // ==========================================
    // Send Verification Email
    // ==========================================
    void sendVerificationEmail(
            String to,
            String username,
            String verificationLink
    );

    // ==========================================
    // Send Generic Email
    // ==========================================
    void sendEmail(
            String to,
            String subject,
            String body
    );

}