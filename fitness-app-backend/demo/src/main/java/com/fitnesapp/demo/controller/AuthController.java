package com.fitnesapp.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitnesapp.demo.dto.UserResponseDto;
import com.fitnesapp.demo.models.User;
import com.fitnesapp.demo.services.AuthService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @GetMapping("/me")
    public ResponseEntity<?> getUserDetails(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
    
        Optional<User> user = authService.validateTokenAndGetUser(token);
    
        if (user.isEmpty()) {
            return ResponseEntity.status(401).body("Token geçersiz veya süresi dolmuş");
        }
    
        // Şifreyi gizlemek için DTO ile dön
        return ResponseEntity.ok(new UserResponseDto(user.get()));
    }
    
}
