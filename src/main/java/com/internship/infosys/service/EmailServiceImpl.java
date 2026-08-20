package com.internship.infosys.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    // =====================================================
    // BREVO CONFIGURATION
    // =====================================================

    @Value("${brevo.api-key}")
    private String brevoApiKey;

    @Value("${brevo.sender-email}")
    private String senderEmail;

    @Value("${brevo.sender-name:Cloud Security Monitoring System}")
    private String senderName;

    private final HttpClient httpClient =
            HttpClient.newHttpClient();

    // =====================================================
    // GENERIC EMAIL
    // =====================================================

    @Override
    public void sendEmail(
            String to,
            String subject,
            String body) {

        try {

            String jsonBody = """
                    {
                      "sender": {
                        "name": "%s",
                        "email": "%s"
                      },
                      "to": [
                        {
                          "email": "%s"
                        }
                      ],
                      "subject": "%s",
                      "textContent": "%s"
                    }
                    """
                    .formatted(
                            escapeJson(senderName),
                            escapeJson(senderEmail),
                            escapeJson(to),
                            escapeJson(subject),
                            escapeJson(body)
                    );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(
                            "https://api.brevo.com/v3/smtp/email"
                    ))
                    .header(
                            "accept",
                            "application/json"
                    )
                    .header(
                            "api-key",
                            brevoApiKey
                    )
                    .header(
                            "content-type",
                            "application/json"
                    )
                    .POST(
                            HttpRequest.BodyPublishers.ofString(
                                    jsonBody
                            )
                    )
                    .build();

            HttpResponse<String> response =
                    httpClient.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            System.out.println(
                    "======================================"
            );

            System.out.println(
                    "BREVO EMAIL RESPONSE"
            );

            System.out.println(
                    "Status: " + response.statusCode()
            );

            System.out.println(
                    "Response: " + response.body()
            );

            System.out.println(
                    "======================================"
            );

            if (response.statusCode() < 200
                    || response.statusCode() >= 300) {

                throw new RuntimeException(
                        "Brevo email sending failed. HTTP "
                                + response.statusCode()
                                + " : "
                                + response.body()
                );
            }

            System.out.println(
                    "✅ Email sent successfully to: "
                            + to
            );

        } catch (Exception e) {

            System.out.println(
                    "❌ BREVO EMAIL ERROR"
            );

            System.out.println(
                    "Recipient: " + to
            );

            System.out.println(
                    "Error: " + e.getMessage()
            );

            throw new RuntimeException(
                    "Unable to send email through Brevo.",
                    e
            );
        }
    }

    // =====================================================
    // VERIFICATION EMAIL
    // =====================================================

    @Override
    public void sendVerificationEmail(
            String to,
            String username,
            String verificationLink) {

        String subject =
                "Verify Your Cloud Security Monitoring System Account";

        String body = """
                Hello %s,

                Welcome to Cloud Security Monitoring System.

                Thank you for registering.

                Please click the link below to verify your account:

                %s

                This verification link will expire in 24 hours.

                If you did not create this account,
                please ignore this email.

                Regards,
                Cloud Security Monitoring System Team
                """
                .formatted(
                        username,
                        verificationLink
                );

        sendEmail(
                to,
                subject,
                body
        );
    }

    // =====================================================
    // JSON ESCAPE
    // =====================================================

    private String escapeJson(String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "\\r")
                .replace("\n", "\\n");
    }
}
