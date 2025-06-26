package com.fitnesapp.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fitnesapp.demo.dto.CustomWorkoutProgramDto;
import com.fitnesapp.demo.models.CustomWorkoutProgram;
import com.fitnesapp.demo.services.CustomWorkoutProgramService;

@RestController
@RequestMapping("/api/custom-workout-programs")
public class CustomWorkoutProgramController {

    @Autowired
    private CustomWorkoutProgramService customWorkoutProgramService;

    @PostMapping
    public ResponseEntity<?> saveCustomProgram(@RequestBody CustomWorkoutProgramDto dto) {
        CustomWorkoutProgram saved = customWorkoutProgramService.saveCustomProgram(dto);
        // Sadece id, title, userId gibi düz alanları döndür:
        Map<String, Object> response = new HashMap<>();
        response.put("id", saved.getId());
        response.put("title", saved.getTitle());
        response.put("userId", saved.getUserId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CustomWorkoutProgram>> getUserPrograms(@PathVariable String userId) {
        return ResponseEntity.ok(customWorkoutProgramService.getUserPrograms(userId));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomProgram(@PathVariable Long id, @RequestParam String userId) {
        customWorkoutProgramService.deleteCustomProgram(id, userId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Custom program deleted");
        return ResponseEntity.ok(response);
    }
}
