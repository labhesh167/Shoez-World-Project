package com.example.ShoezWorld.Service;

import org.springframework.stereotype.Service;
import com.example.ShoezWorld.Model.User;
import com.example.ShoezWorld.Repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User register(User user) {
        System.out.println("Saving user: " + user);
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String id) {
        return userRepository.findByEmail(id);
    }

    public Optional<User> login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            return userOpt;
        }
        return Optional.empty();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public boolean deleteByUsername(String username) {
        int deleted = userRepository.deleteByUsername(username);
        return deleted > 0; // true if any rows were deleted
    }
}