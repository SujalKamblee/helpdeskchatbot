package com.helpdesk.chatbot.helpdesk_chatbot_backend.controller;


import com.helpdesk.chatbot.helpdesk_chatbot_backend.dto.TopicResponse ;
import com.helpdesk.chatbot.helpdesk_chatbot_backend.services.ChatbotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class ChatbotController {

    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    // Health check for quick verification
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }

    // List active topics (for main buttons)
    @GetMapping
    public ResponseEntity<List<TopicResponse>> getAllTopics() {
        return ResponseEntity.ok(chatbotService.getAllTopics());
    }

    // Get a single topic with its ordered steps
    @GetMapping("/{id}")
    public ResponseEntity<TopicResponse> getTopic(@PathVariable Long id) {
        return ResponseEntity.ok(chatbotService.getTopicWithSteps(id));
    }

    // Keyword-based search across title/keywords
    @GetMapping("/search")
    public ResponseEntity<List<TopicResponse>> search(@RequestParam("query") String query) {
        return ResponseEntity.ok(chatbotService.searchTopics(query));
    }
}
