package com.helpdesk.chatbot.helpdesk_chatbot_backend.controller;

import com.helpdesk.chatbot.helpdesk_chatbot_backend.dto.LoginRequest;
import com.helpdesk.chatbot.helpdesk_chatbot_backend.dto.LoginResponse;
import com.helpdesk.chatbot.helpdesk_chatbot_backend.Security.AuthService;
import com.helpdesk.chatbot.helpdesk_chatbot_backend.Security.JwtService;
import com.helpdesk.chatbot.helpdesk_chatbot_backend.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;
    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService; this.jwtService = jwtService;
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        Optional<User> userOptional = authService.validateUser(req.getEmail(), req.getPassword());

        if (userOptional.isPresent()) {
            // On success, generate a token and return it in a LoginResponse
            String token = jwtService.generateToken(userOptional.get());
            return ResponseEntity.ok(new LoginResponse(token));
        } else {
            // On failure, return a 401 Unauthorized with an error message
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
