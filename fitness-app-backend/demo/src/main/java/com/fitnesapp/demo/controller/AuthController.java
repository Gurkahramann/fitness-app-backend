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
import com.fitnesapp.demo.services.NodeAuthClientService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.http.MediaType;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private NodeAuthClientService nodeAuthClientService;

    @GetMapping("/me")
    public ResponseEntity<?> getUserDetails(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        // Sadece kullanıcı bilgisi endpointini çağır
        ResponseEntity<String> userInfoResponse = nodeAuthClientService.getUserInfo(token);
        if (!userInfoResponse.getStatusCode().is2xxSuccessful() || userInfoResponse.getBody() == null) {
            return ResponseEntity.status(401).body("Token geçersiz, süresi dolmuş veya kullanıcı bulunamadı.");
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userInfoResponse.getBody());
    }

    //@GetMapping("/userinfo")
    /*
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Token eksik veya geçersiz.");
        }
        String token = authHeader.substring(7);
        ResponseEntity<String> userInfoResponse = nodeAuthClientService.getUserInfo(token);
        if (!userInfoResponse.getStatusCode().is2xxSuccessful() || userInfoResponse.getBody() == null) {
            return ResponseEntity.status(401).body("Token geçersiz, süresi dolmuş veya kullanıcı bulunamadı.");
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userInfoResponse.getBody());
    }
                */
}
