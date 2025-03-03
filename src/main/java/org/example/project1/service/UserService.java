package org.example.project1.service;

import org.example.project1.DTO.AuthResponse;
import org.example.project1.Entity.User;
import org.example.project1.repository.UserRepository;
import org.example.project1.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // 🔥 New method to check if the user exists
    public boolean userExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // ✅ Encrypt password before saving
        return userRepository.save(user);
    }

    public Optional<AuthResponse> authenticateUser(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // ✅ Check if password matches
            if (passwordEncoder.matches(password, user.getPassword())) {
                String token = jwtUtil.generateToken(username);
                return Optional.of(new AuthResponse(token, username));
            }
        }
        return Optional.empty(); // ❌ Authentication failed
    }
}
