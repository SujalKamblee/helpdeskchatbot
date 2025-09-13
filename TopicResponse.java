package com.helpdesk.chatbot.helpdesk_chatbot_backend.dto;


import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class TopicResponse {
    Long id;
    String title;
    String keywords;              // include or omit in public APIs as needed
    List<StepResponse> steps;     // can be null/empty for list endpoints
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}

