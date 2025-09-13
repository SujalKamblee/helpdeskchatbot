package com.helpdesk.chatbot.helpdesk_chatbot_backend.services;

import com.helpdesk.chatbot.helpdesk_chatbot_backend.dto.StepResponse;
import com.helpdesk.chatbot.helpdesk_chatbot_backend.dto.TopicResponse;
import com.helpdesk.chatbot.helpdesk_chatbot_backend.entity.Step;
import com.helpdesk.chatbot.helpdesk_chatbot_backend.entity.Topic;
import com.helpdesk.chatbot.helpdesk_chatbot_backend.repository.StepRepository;
import com.helpdesk.chatbot.helpdesk_chatbot_backend.repository.TopicRepository;
import com.helpdesk.chatbot.helpdesk_chatbot_backend.dto.StepRequest;
import com.helpdesk.chatbot.helpdesk_chatbot_backend.dto.TopicRequest ;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdminService {

    private final TopicRepository topicRepository;
    private final StepRepository stepRepository;

    public AdminService(TopicRepository topicRepository, StepRepository stepRepository) {
        this.topicRepository = topicRepository;
        this.stepRepository = stepRepository;
    }
    @Transactional(readOnly = true)
    public List<TopicResponse> listAllTopicsForAdmin() {
        return topicRepository.findAll().stream()
                .map(t -> TopicResponse.builder()
                        .id(t.getId())
                        .title(t.getTitle())
                        .keywords(t.getKeywords())
                        .createdAt(t.getCreatedAt())
                        .updatedAt(t.getUpdatedAt())
                        .build())
                .toList();
    }

    @Transactional
    public TopicResponse createTopic(TopicRequest req) {
        Topic topic = new Topic();
        topic.setTitle(req.getTitle());
        topic.setKeywords(req.getKeywords());
        topic.setIsActive(true);

        Topic saved = topicRepository.save(topic);

        List<Step> steps = req.getSteps().stream()
                .filter(sr -> sr.getStepText() != null && !sr.getStepText().trim().isEmpty()) // 🚀 ignore empty steps
                .map(sr -> {
                    Step s = new Step();
                    s.setStepOrder(sr.getStepOrder());
                    s.setStepText(sr.getStepText());
                    s.setTopic(saved);
                    return s;
                })
                .toList();
        stepRepository.saveAll(steps);

        return toTopicResponseWithSteps(saved, steps);
    }

    @Transactional
    public TopicResponse updateTopic(Long id, TopicRequest req) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Topic not found: " + id));

        topic.setTitle(req.getTitle());
        topic.setKeywords(req.getKeywords());

        // 1️⃣ Ensure old steps are deleted & flushed
        stepRepository.deleteByTopicId(topic.getId());
        stepRepository.flush(); // 🚀 important

        // 2️⃣ Validate duplicates in request
        Set<Integer> seenOrders = new HashSet<>();
        for (StepRequest sr : req.getSteps()) {
            if (!seenOrders.add(sr.getStepOrder())) {
                throw new IllegalArgumentException("Duplicate step order: " + sr.getStepOrder());
            }
        }

        // 3️⃣ Filter out blanks
        List<Step> newSteps = req.getSteps().stream()
                .filter(sr -> sr.getStepText() != null && !sr.getStepText().trim().isEmpty())
                .map(sr -> {
                    Step s = new Step();
                    s.setStepOrder(sr.getStepOrder());
                    s.setStepText(sr.getStepText());
                    s.setTopic(topic);
                    return s;
                })
                .toList();

        stepRepository.saveAll(newSteps);

        Topic saved = topicRepository.save(topic);
        return toTopicResponseWithSteps(saved, newSteps);
    }


    @Transactional
    public void hardDeleteTopic(Long id) {
        // First delete steps, then topic
        stepRepository.deleteByTopicId(id);
        topicRepository.deleteById(id);
    }

    // Optionally add: activate topic
    @Transactional
    public void activateTopic(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Topic not found: " + id));
        topic.setIsActive(true);
        topicRepository.save(topic);
    }

    // Mapping helpers

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

