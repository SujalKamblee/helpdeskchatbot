package com.helpdesk.chatbot.helpdesk_chatbot_backend.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI helpdeskOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Helpdesk Chatbot API")
                        .description("Public and Admin APIs for topics and steps")
                        .version("v1.0.0"));
    }
}
