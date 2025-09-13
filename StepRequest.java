package com.helpdesk.chatbot.helpdesk_chatbot_backend.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StepRequest {

    @NotNull(message = "Step order is required")
    private Integer stepOrder;

    private String stepText;
}

