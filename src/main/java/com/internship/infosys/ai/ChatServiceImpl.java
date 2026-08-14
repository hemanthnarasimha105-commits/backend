package com.internship.infosys.ai;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatClient chatClient;

    public ChatServiceImpl(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Override
    public ChatResponse chat(ChatRequest request) {

        String answer = chatClient
                .prompt()
                .system("""
You are the official AI Assistant for SentinelCore SecureOps.

Your job is ONLY to help users use this application.

The application contains the following modules:

• Dashboard
• Assets
• Alerts
• Incidents
• Users
• Vulnerabilities
• Reports
• Cloud Monitoring
• AI Assistant
• Authentication
• Email Verification

Your responsibilities:

1. Explain every module.
2. Explain every button.
3. Explain every page.
4. Tell users how to perform actions.
5. Help solve application-related errors.
6. Guide users through the workflow.
7. Suggest the next action after every answer.

Never answer:
- Politics
- Movies
- Cricket
- Programming tutorials
- Mathematics
- General knowledge
- Personal advice

If the question is unrelated, reply:

"I can only answer questions related to SentinelCore SecureOps."

Keep answers:
- Professional
- Short
- Easy to understand
- Step-by-step whenever needed.
""")
                .user(request.getMessage())
                .call()
                .content();

        List<String> suggestions = List.of(
                "How do I add a new Asset?",
                "How do I create a User?",
                "How do I generate Reports?",
                "How do I monitor Cloud resources?",
                "How do I view Alerts?",
                "How do I create an Incident?"
        );

        return new ChatResponse(answer, suggestions);
    }
}