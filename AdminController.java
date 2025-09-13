package com.helpdesk.chatbot.helpdesk_chatbot_backend.controller;

import com.helpdesk.chatbot.helpdesk_chatbot_backend.dto.TopicRequest ;
import com.helpdesk.chatbot.helpdesk_chatbot_backend.dto.TopicResponse;
import com.helpdesk.chatbot.helpdesk_chatbot_backend.services.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/topics")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public ResponseEntity<List<TopicResponse>> listAllForAdmin() {
        return ResponseEntity.ok(adminService.listAllTopicsForAdmin());
    }

    @PostMapping
    public ResponseEntity<TopicResponse> create(@Valid @RequestBody TopicRequest request) {
        TopicResponse created = adminService.createTopic(request);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicResponse> update(@PathVariable Long id,
                                                @Valid @RequestBody TopicRequest request) {
        TopicResponse updated = adminService.updateTopic(id, request);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> hardDelete(@PathVariable Long id) {
        adminService.hardDeleteTopic(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable Long id) {
        adminService.activateTopic(id);
        return ResponseEntity.noContent().build();
    }
}
