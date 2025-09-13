package com.helpdesk.chatbot.helpdesk_chatbot_backend;

import com.helpdesk.chatbot.helpdesk_chatbot_backend.entity.User;
import com.helpdesk.chatbot.helpdesk_chatbot_backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class HelpdeskChatbotBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelpdeskChatbotBackendApplication.class, args);
	}

	/**
	 * This bean runs on startup and inserts test users into the database.
	 * This is useful for development and testing.
	 */
	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			// Create an ADMIN user if one doesn't exist
			if (userRepository.findByEmailAndActiveTrue("admin@test.com").isEmpty()) {
				User admin = new User("admin@test.com", passwordEncoder.encode("admin123"), true, Set.of("ADMIN", "AGENT"));
				userRepository.save(admin);
				System.out.println("Created ADMIN user: admin@test.com / admin123");
			}

			// Create a regular AGENT user if one doesn't exist
			if (userRepository.findByEmailAndActiveTrue("agent@test.com").isEmpty()) {
				User agent = new User("agent@test.com", passwordEncoder.encode("agent123"), true, Set.of("AGENT"));
				userRepository.save(agent);
				System.out.println("Created AGENT user: agent@test.com / agent123");
			}
		};
	}
}
