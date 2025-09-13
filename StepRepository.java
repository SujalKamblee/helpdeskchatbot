package com.helpdesk.chatbot.helpdesk_chatbot_backend.repository;


import com.helpdesk.chatbot.helpdesk_chatbot_backend.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StepRepository extends JpaRepository<Step, Long> {

    List<Step> findByTopicIdOrderByStepOrderAsc(Long topicId);

    long deleteByTopicId(Long topicId);
}

