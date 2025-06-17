package com.fitnesapp.demo.controller;

import com.fitnesapp.demo.dto.WorkoutProgramDto;
import com.fitnesapp.demo.models.WorkoutProgram;
import com.fitnesapp.demo.services.WorkoutProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workout-programs")
public class WorkoutProgramController {

    @Autowired
    private WorkoutProgramService workoutProgramService;

    @GetMapping
    public ResponseEntity<List<WorkoutProgramDto>> getAllWorkoutPrograms() {
        return ResponseEntity.ok(workoutProgramService.getAllWorkoutProgramDtos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<WorkoutProgramDto> getWorkoutProgramById(@PathVariable Long id) {
        WorkoutProgramDto dto = workoutProgramService.getWorkoutProgramDtoById(id);
        return ResponseEntity.ok(dto);
    }
} 