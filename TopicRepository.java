package com.helpdesk.chatbot.helpdesk_chatbot_backend.repository;


import com.helpdesk.chatbot.helpdesk_chatbot_backend.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    List<Topic> findByIsActiveTrue();

    Optional<Topic> findByIdAndIsActiveTrue(Long id);

    // Simple keyword search over title and keywords (case-insensitive)
    @Query("""
           SELECT t FROM Topic t
           WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :q, '%'))
              OR LOWER(t.keywords) LIKE LOWER(CONCAT('%', :q, '%'))
           """)
    List<Topic> search(String q);
}
