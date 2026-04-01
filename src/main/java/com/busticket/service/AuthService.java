package com.busticket.service;

import com.busticket.entity.User;
import com.busticket.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * AuthService - Handles user registration and authentication.
 * Uses simple password comparison (no BCrypt for student-friendliness).
 */
@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Register a new user.
     */
    public User register(String fullName, String email, String password, String phone) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered: " + email);
        }

        User user = new User(fullName, email, password, phone, "USER");
        return userRepository.save(user);
    }

    /**
     * Login with email and password.
     */
    public User login(String email, String password) {
        Optional<User> optUser = userRepository.findByEmail(email);

        if (optUser.isEmpty()) {
            throw new RuntimeException("No account found with email: " + email);
        }

        User user = optUser.get();
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Incorrect password.");
        }

        return user;
    }

    /**
     * Find user by ID.
     */
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found."));
    }

    /**
     * Get all users.
     */
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
