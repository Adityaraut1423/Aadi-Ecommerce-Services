package com.aadiandjava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.aadiandjava.entity.User;
import com.aadiandjava.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Allows cross-origin requests from your Firebase domain
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // Standard Spring BCrypt encoder instance
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // DTO for capturing JSON payload from login request
    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // 1. Fetch user directly from database by email (returns User object directly)
        User user = userRepository.findByEmail(loginRequest.getEmail());

        if (user == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        // 2. Validate password (checks BCrypt hash or plain-text fallback)
        boolean isPasswordValid = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()) 
                || loginRequest.getPassword().equals(user.getPassword());

        if (!isPasswordValid) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        // 3. Clear sensitive password string before sending response payload
        user.setPassword(null);

        // ✅ 4. Return complete User JSON object (HTTP 200 OK)
        return ResponseEntity.ok(user);
    }
}