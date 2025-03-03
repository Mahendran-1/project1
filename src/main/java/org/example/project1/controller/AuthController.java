package org.example.project1.controller;

import org.example.project1.DTO.AuthResponse;
import org.example.project1.Entity.User;
import org.example.project1.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200") // Allow Angular requests
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userService.userExists(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "User already exists!")); // ❌ 409 Conflict
        }
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser); // ✅ 201 Created
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<AuthResponse> authResponse = userService.authenticateUser(user.getUsername(), user.getPassword());

        if (authResponse.isPresent()) {
            return ResponseEntity.ok(authResponse.get()); // ✅ 200 OK
        }

        // Check if user exists before returning Unauthorized
        if (!userService.userExists(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User does not exist!")); // ❌ 404 Not Found
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid credentials!")); // ❌ 401 Unauthorized
    }

    @PostMapping("/encode-password")
    public ResponseEntity<?> encodePassword(@RequestBody Map<String, String> requestBody) {
        if (!requestBody.containsKey("password") || requestBody.get("password").isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Password is required!")); // ❌ 400 Bad Request
        }

        String encodedPassword = passwordEncoder.encode(requestBody.get("password"));
        return ResponseEntity.ok(Map.of("encodedPassword", encodedPassword)); // ✅ 200 OK
    }
}
