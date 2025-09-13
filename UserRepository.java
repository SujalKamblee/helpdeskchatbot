package com.helpdesk.chatbot.helpdesk_chatbot_backend.repository;

import com.helpdesk.chatbot.helpdesk_chatbot_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndActiveTrue(String email);
}