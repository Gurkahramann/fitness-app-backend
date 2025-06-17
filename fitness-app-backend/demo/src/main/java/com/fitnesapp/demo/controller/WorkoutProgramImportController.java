package com.fitnesapp.demo.controller;

import com.fitnesapp.demo.dto.WorkoutProgramImportDto;
import com.fitnesapp.demo.services.WorkoutProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/import")
public class WorkoutProgramImportController {

    @Autowired
    private WorkoutProgramService workoutProgramService;
    @PostMapping("/workout-programs")
    public ResponseEntity<?> importWorkoutPrograms(@RequestBody List<WorkoutProgramImportDto> dtos) {
        int count = workoutProgramService.importWorkoutPrograms(dtos);
        return ResponseEntity.ok(count + " workout program(s) imported successfully!");
    }
}