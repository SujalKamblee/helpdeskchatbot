package com.helpdesk.chatbot.helpdesk_chatbot_backend.services;


import com.helpdesk.chatbot.helpdesk_chatbot_backend.dto.StepResponse;
import com.helpdesk.chatbot.helpdesk_chatbot_backend.dto.TopicResponse;
import com.helpdesk.chatbot.helpdesk_chatbot_backend.entity.Step;
import com.helpdesk.chatbot.helpdesk_chatbot_backend.entity.Topic;
import com.helpdesk.chatbot.helpdesk_chatbot_backend.repository.StepRepository;
import com.helpdesk.chatbot.helpdesk_chatbot_backend.repository.TopicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ChatbotService {

    private final TopicRepository topicRepository;
    private final StepRepository stepRepository;

    public ChatbotService(TopicRepository topicRepository, StepRepository stepRepository) {
        this.topicRepository = topicRepository;
        this.stepRepository = stepRepository;
    }

    public List<TopicResponse> getAllTopics() {
        return topicRepository.findByIsActiveTrue()
                .stream()
                .map(this::toTopicResponseWithoutSteps)
                .toList();
    }

    public TopicResponse getTopicWithSteps(Long id) {
        Topic topic = topicRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new IllegalArgumentException("Topic not found or inactive: " + id));

        List<Step> steps = stepRepository.findByTopicIdOrderByStepOrderAsc(topic.getId());
        return toTopicResponseWithSteps(topic, steps);
    }

    public List<TopicResponse> searchTopics(String q) {
        if (q == null || q.isBlank()) {
            return List.of();
        }
        return topicRepository.search(q.trim())
                .stream()
                .filter(t -> Boolean.TRUE.equals(t.getIsActive()))
                .map(this::toTopicResponseWithoutSteps)
                .toList();
    }

    // Mapping helpers

    private TopicResponse toTopicResponseWithoutSteps(Topic t) {
        return TopicResponse.builder()
                .id(t.getId())
                .title(t.getTitle())
                .keywords(t.getKeywords())
                .createdAt(t.getCreatedAt())
                .updatedAt(t.getUpdatedAt())
                .build();
    }

    private TopicResponse toTopicResponseWithSteps(Topic t, List<Step> steps) {
        List<StepResponse> stepDtos = steps.stream()
                .map(s -> StepResponse.builder()
                        .id(s.getId())
                        .stepOrder(s.getStepOrder())
                        .stepText(s.getStepText())
                        .build())
                .toList();

        return TopicResponse.builder()
                .id(t.getId())
                .title(t.getTitle())
                .keywords(t.getKeywords())
                .steps(stepDtos)
                .createdAt(t.getCreatedAt())
                .updatedAt(t.getUpdatedAt())
                .build();
    }
}
