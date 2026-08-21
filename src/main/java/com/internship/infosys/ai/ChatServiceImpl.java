package com.internship.infosys.ai;

import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class ChatServiceImpl implements ChatService {

    @Override
    public ChatResponse chat(ChatRequest request) {

        if (request == null ||
                request.getMessage() == null ||
                request.getMessage().trim().isEmpty()) {

            return ChatResponse.message(
                    "Please enter a message."
            );
        }

        String message = request.getMessage()
                .trim()
                .toLowerCase(Locale.ROOT);

        Intent intent = detectIntent(message);

        return processIntent(intent, request);
    }

    // =====================================================
    // INTENT DETECTION
    // =====================================================

    private Intent detectIntent(String message) {

        // =================================================
        // DASHBOARD
        // =================================================

        if (containsAny(
                message,
                "dashboard",
                "home",
                "main page",
                "overview",
                "security overview"
        )) {
            return Intent.DASHBOARD;
        }

        // =================================================
        // ASSETS
        // =================================================

        if (containsAny(
                message,
                "asset",
                "assets",
                "devices",
                "servers",
                "infrastructure"
        )) {
            return Intent.ASSETS;
        }

        // =================================================
        // ALERTS
        // =================================================

        if (containsAny(
                message,
                "alert",
                "alerts",
                "warning",
                "warnings"
        )) {
            return Intent.ALERTS;
        }

        // =================================================
        // INCIDENTS
        // =================================================

        if (containsAny(
                message,
                "incident",
                "incidents",
                "security incident"
        )) {
            return Intent.INCIDENTS;
        }

        // =================================================
        // VULNERABILITIES
        // =================================================

        if (containsAny(
                message,
                "vulnerability",
                "vulnerabilities",
                "cve",
                "security weakness"
        )) {
            return Intent.VULNERABILITIES;
        }

        // =================================================
        // CLOUD
        // =================================================

        if (containsAny(
                message,
                "cloud",
                "cloud security",
                "cloud monitoring"
        )) {
            return Intent.CLOUD;
        }

        // =================================================
        // REPORTS
        // =================================================

        if (containsAny(
                message,
                "report",
                "reports",
                "generate report",
                "security report"
        )) {
            return Intent.REPORTS;
        }

        // =================================================
        // USERS
        // =================================================

        if (containsAny(
                message,
                "user",
                "users",
                "user management",
                "accounts"
        )) {
            return Intent.USERS;
        }

        // =================================================
        // LOGOUT
        // =================================================

        if (containsAny(
                message,
                "logout",
                "log out",
                "sign out",
                "exit"
        )) {
            return Intent.LOGOUT;
        }

        // =================================================
        // HELP
        // =================================================

        if (containsAny(
                message,
                "help",
                "what can you do",
                "commands",
                "options"
        )) {
            return Intent.HELP;
        }

        // =================================================
        // SECURITY ADVICE
        // =================================================

        if (containsAny(
                message,
                "security advice",
                "security recommendation",
                "secure my system",
                "how to secure",
                "security tips"
        )) {
            return Intent.SECURITY_ADVICE;
        }

        // =================================================
        // GREETING
        // =================================================

        if (containsAny(
                message,
                "hi",
                "hello",
                "hey",
                "good morning",
                "good afternoon",
                "good evening"
        )) {
            return Intent.GREETING;
        }

        return Intent.UNKNOWN;
    }

    // =====================================================
    // PROCESS INTENT
    // =====================================================

    private ChatResponse processIntent(
            Intent intent,
            ChatRequest request) {

        return switch (intent) {

            case DASHBOARD ->
                    ChatResponse.navigate(
                            "Opening Security Dashboard...",
                            "/dashboard"
                    );

            case ASSETS ->
                    ChatResponse.navigate(
                            "Opening Assets...",
                            "/assets"
                    );

            case ALERTS ->
                    ChatResponse.navigate(
                            "Opening Security Alerts...",
                            "/alerts"
                    );

            case INCIDENTS ->
                    ChatResponse.navigate(
                            "Opening Incident Management...",
                            "/incidents"
                    );

            case VULNERABILITIES ->
                    ChatResponse.navigate(
                            "Opening Vulnerability Management...",
                            "/vulnerabilities"
                    );

            case CLOUD ->
                    ChatResponse.navigate(
                            "Opening Cloud Security Monitoring...",
                            "/cloud"
                    );

            case REPORTS ->
                    ChatResponse.navigate(
                            "Opening Security Reports...",
                            "/reports"
                    );

            case USERS ->
                    ChatResponse.navigate(
                            "Opening User Management...",
                            "/users"
                    );

            case LOGOUT ->
                    ChatResponse.message(
                            "Logging out..."
                    );

            case HELP ->
                    ChatResponse.message(
                            getHelpMessage()
                    );

            case SECURITY_ADVICE ->
                    ChatResponse.message(
                            getSecurityAdvice()
                    );

            case GREETING ->
                    ChatResponse.message(
                            "Hello! 👋 I am your Cloud Security Monitoring Assistant. "
                            + "I can help you navigate the dashboard, assets, alerts, "
                            + "incidents, vulnerabilities, reports and cloud security."
                    );

            case UNKNOWN ->
                    handleUnknown(
                            request.getMessage()
                    );
        };
    }

    // =====================================================
    // UNKNOWN
    // =====================================================

    private ChatResponse handleUnknown(
            String originalMessage) {

        return ChatResponse.message(
                """
                I didn't fully understand that.

                You can ask me:

                • Go to dashboard
                • Open assets
                • Show alerts
                • Open incidents
                • Show vulnerabilities
                • Open cloud monitoring
                • Generate reports
                • Manage users
                • Give me security advice
                • Help

                Try asking in natural language.
                """
        );
    }

    // =====================================================
    // HELP
    // =====================================================

    private String getHelpMessage() {

        return """
                🤖 Cloud Security Monitoring Assistant

                I can help you with:

                📊 Dashboard
                "Open dashboard"

                🖥️ Assets
                "Show assets"

                🚨 Alerts
                "Show security alerts"

                🔥 Incidents
                "Open incidents"

                🛡️ Vulnerabilities
                "Show vulnerabilities"

                ☁️ Cloud
                "Open cloud monitoring"

                📄 Reports
                "Show reports"

                👥 Users
                "Open user management"

                🔐 Security
                "Give me security advice"

                You can type these commands naturally.
                """;
    }

    // =====================================================
    // SECURITY ADVICE
    // =====================================================

    private String getSecurityAdvice() {

        return """
                🔐 Security Recommendations

                1. Monitor critical assets regularly.
                2. Investigate critical alerts immediately.
                3. Keep systems and dependencies patched.
                4. Review vulnerabilities regularly.
                5. Use strong authentication.
                6. Monitor suspicious activity.
                7. Maintain incident response procedures.
                8. Generate regular security reports.
                9. Review user permissions.
                10. Keep security logs for investigation.
                """;
    }

    // =====================================================
    // KEYWORD CHECK
    // =====================================================

    private boolean containsAny(
            String message,
            String... keywords) {

        for (String keyword : keywords) {

            if (message.contains(keyword)) {
                return true;
            }
        }

        return false;
    }
}
