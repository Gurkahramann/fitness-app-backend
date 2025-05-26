package com.fitnesapp.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitnesapp.demo.dto.UserInfoDto;
import com.fitnesapp.demo.dto.UserResponseDto;
import com.fitnesapp.demo.models.User;
import com.fitnesapp.demo.security.JwtUtil;
import com.fitnesapp.demo.services.AuthService;

import jakarta.servlet.http.HttpServletRequest;

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
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/userinfo")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Token eksik veya geçersiz.");
        }

        String token = authHeader.substring(7);
        String userId = jwtUtil.extractUserId(token); // static değil!

        UserInfoDto userInfo = authService.getUserInfo(userId);

        if (userInfo == null) {
            return ResponseEntity.status(404).body("Kullanıcı bulunamadı.");
        }

        return ResponseEntity.ok(userInfo);
    }

    
}
