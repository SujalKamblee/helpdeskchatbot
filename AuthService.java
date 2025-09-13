package com.helpdesk.chatbot.helpdesk_chatbot_backend.Security;

import com.helpdesk.chatbot.helpdesk_chatbot_backend.entity.User;
import com.helpdesk.chatbot.helpdesk_chatbot_backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Validates user credentials and returns the User object if successful.
     */
    public Optional<User> validateUser(String email, String password) {
        return userRepository.findByEmailAndActiveTrue(email)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }
}