package com.helpdesk.chatbot.helpdesk_chatbot_backend.dto;



import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StepResponse {
    Long id;
    Integer stepOrder;
    String stepText;
}
