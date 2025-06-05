package com.fitnesapp.demo.controller;

import com.fitnesapp.demo.dto.WorkoutDayRequestDto;
import com.fitnesapp.demo.models.WorkoutDay;
import com.fitnesapp.demo.services.WorkoutDayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workout-days")
public class WorkoutDayController {
    private final WorkoutDayService workoutDayService;

    public WorkoutDayController(WorkoutDayService workoutDayService) {
        this.workoutDayService = workoutDayService;
    }

    @PostMapping
    public ResponseEntity<WorkoutDay> createWorkoutDay(@RequestBody WorkoutDayRequestDto request) {
        WorkoutDay created = workoutDayService.createWorkoutDayWithExercise(
            request.getUserId(), request.getDate(), request.getExerciseId()
        );
        return ResponseEntity.ok(created);
    }
}