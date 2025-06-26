package com.fitnesapp.demo.controller;

import com.fitnesapp.demo.dto.UserWorkoutProgramDto;
import com.fitnesapp.demo.dto.UserWorkoutProgramResponseDto;
import com.fitnesapp.demo.services.UserWorkoutProgramService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-workout-programs")
public class UserWorkoutProgramController {

    @Autowired
    private UserWorkoutProgramService userWorkoutProgramService;

    @PostMapping
    public ResponseEntity<Map<String, String>> saveUserWorkoutProgram(@RequestBody UserWorkoutProgramDto dto) {
        userWorkoutProgramService.saveUserWorkoutProgram(dto);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Program başarıyla kaydedildi");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserWorkoutProgramResponseDto>> getUserPrograms(@PathVariable String userId) {
        List<UserWorkoutProgramResponseDto> result = userWorkoutProgramService.getUserProgramsWithDetails(userId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{userId}/{programId}")
    public ResponseEntity<Map<String, String>> deleteUserWorkoutProgram(
            @PathVariable String userId,
            @PathVariable String programId) {
        userWorkoutProgramService.deleteUserWorkoutProgram(userId, programId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Program başarıyla kaldırıldı");
        return ResponseEntity.ok(response);
    }
} 