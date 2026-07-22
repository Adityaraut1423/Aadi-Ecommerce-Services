package com.aadiandjava.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aadiandjava.entity.User;
import com.aadiandjava.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // 👈 Injecting our new BCrypt encoder

    // ==========================================
    // REGISTER USER LOGIC
    // ==========================================
    public User registerUser(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser != null) {
            throw new RuntimeException("Email is already in use.");
        }

        // Assign default role if missing
        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            user.setRole("CUSTOMER");
        }

        // 🌟 ENCRYPT THE PASSWORD BEFORE SAVING TO DATABASE
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    // ==========================================
    // LOGIN USER LOGIC
    // ==========================================
    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);

        // 🌟 USE .matches() TO COMPARE PLAIN TEXT WITH HASHED PASSWORD
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password.");
        }

        return user;
    }

    // ==========================================
    // GET USER BY ID LOGIC
    // ==========================================
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    // ==========================================
    // GET ALL USERS
    // ==========================================
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ==========================================
    // DELETE USER
    // ==========================================
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found in the database.");
        }
        userRepository.deleteById(id);
    }
}