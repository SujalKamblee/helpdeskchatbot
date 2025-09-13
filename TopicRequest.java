package com.helpdesk.chatbot.helpdesk_chatbot_backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class TopicRequest {

    @NotBlank(message = "Title is required")
    private String title;

    // Optional comma-separated keywords
    private String keywords;

    @NotEmpty(message = "At least one step is required")
    @Valid
    private List<StepRequest> steps;
}
