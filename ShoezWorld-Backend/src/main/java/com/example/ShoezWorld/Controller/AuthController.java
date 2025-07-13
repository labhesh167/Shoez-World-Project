package com.example.ShoezWorld.Controller;

import com.example.ShoezWorld.Model.User;
import com.example.ShoezWorld.Service.UserService;
import com.example.ShoezWorld.util.jwtUtil;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private jwtUtil jwtUtil;

    // Register a new user (check for existing username and email)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username already taken"));
        }
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email already in use"));
        }

        // If no role provided in request, default to "USER"
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }

        User registeredUser = userService.register(user);
        return ResponseEntity.ok(Map.of("message", "User registered successfully", "user", registeredUser));
    }

    // Login: authenticate user, generate JWT with username & role, return token +
    // user info
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> result = userService.login(user.getUsername(), user.getPassword());
        if (result.isPresent()) {
            String token = jwtUtil.generateToken(result.get().getUsername(), result.get().getRole());
            return ResponseEntity.ok(Map.of("token", token, "user", result.get()));
        }
        return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
    }

    // Get list of all users (admin-only)
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String role = jwtUtil.extractRole(token);

        if (!"ADMIN".equals(role)) {
            return ResponseEntity.status(403).body(Map.of("error", "Access denied: ADMINs only."));
        }

        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Delete a user by username (admin-use)
    @DeleteMapping("/users/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        if (userService.deleteByUsername(username)) {
            return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
        }
        return ResponseEntity.status(404).body(Map.of("error", "User not found"));
    }
}
