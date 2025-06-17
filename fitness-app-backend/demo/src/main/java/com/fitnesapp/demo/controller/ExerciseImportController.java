package com.fitnesapp.demo.controller;

import com.fitnesapp.demo.models.Exercise;
import com.fitnesapp.demo.services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/import")
public class ExerciseImportController {

    @Autowired
    private ExerciseService exerciseService;

    @PostMapping("/exercises")
    public ResponseEntity<?> importExercises(@RequestBody List<Exercise> exercises) {
        List<Exercise> saved = exerciseService.saveAll(exercises);
        return ResponseEntity.ok(saved.size() + " exercise(s) imported successfully!");
    }
}