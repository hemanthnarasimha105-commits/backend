package com.internship.infosys.ai;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(
        origins = {
                "http://localhost:5173"
        }
)
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    // =====================================================
    // AI ASK
    // =====================================================

    @PostMapping("/ask")
    public ResponseEntity<ChatResponse> ask(
            @RequestBody ChatRequest request) {

        ChatResponse response =
                chatService.chat(request);

        return ResponseEntity.ok(response);
    }

    // =====================================================
    // AI CHAT
    // =====================================================

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(
            @RequestBody ChatRequest request) {

        ChatResponse response =
                chatService.chat(request);

        return ResponseEntity.ok(response);
    }

    // =====================================================
    // HEALTH
    // =====================================================

    @GetMapping("/health")
    public ResponseEntity<String> health() {

        return ResponseEntity.ok(
                "Cloud Security Monitoring AI Assistant is running."
        );
    }
}
