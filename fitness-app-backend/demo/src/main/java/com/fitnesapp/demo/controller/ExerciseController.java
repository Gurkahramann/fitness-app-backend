package com.fitnesapp.demo.controller;

import com.fitnesapp.demo.models.Exercise;
import com.fitnesapp.demo.repositories.ExerciseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {
    private final ExerciseRepository exerciseRepository;

    public ExerciseController(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @PostMapping
    public ResponseEntity<Exercise> createExercise(@RequestBody Exercise request) {
        Exercise exercise = Exercise.builder()
                .id(request.getId())
                .name(request.getName())
                .type(request.getType())
                .muscleGroup(request.getMuscleGroup())
                .videoUrl(request.getVideoUrl())
                .build();
        Exercise saved = exerciseRepository.save(exercise);
        return ResponseEntity.ok(saved);
    }

}