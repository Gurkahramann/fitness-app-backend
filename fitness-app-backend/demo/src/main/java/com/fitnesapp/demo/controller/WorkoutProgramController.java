package com.fitnesapp.demo.controller;

import com.fitnesapp.demo.models.WorkoutProgram;
import com.fitnesapp.demo.services.WorkoutProgramService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workout-programs")
public class WorkoutProgramController {
    private final WorkoutProgramService workoutProgramService;

    
    public WorkoutProgramController(WorkoutProgramService workoutProgramService) {
        this.workoutProgramService = workoutProgramService;
    }

    @PostMapping
    public ResponseEntity<WorkoutProgram> createWorkoutProgram(@RequestBody WorkoutProgram program) {
        WorkoutProgram saved = workoutProgramService.saveWorkoutProgram(program);
        return ResponseEntity.ok(saved);
    }
} 