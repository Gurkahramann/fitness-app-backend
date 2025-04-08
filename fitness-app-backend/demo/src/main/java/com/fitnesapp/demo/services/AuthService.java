package com.fitnesapp.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitnesapp.demo.models.User;
import com.fitnesapp.demo.repositories.UserRepository;
import com.fitnesapp.demo.security.JwtUtil;
@Service
public class AuthService {
       @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    public Optional<User> validateTokenAndGetUser(String token) {
        if (!jwtUtil.validateToken(token)) return Optional.empty();

        String userId = jwtUtil.extractUserId(token);
        return userRepository.findById(userId);
    }
}
