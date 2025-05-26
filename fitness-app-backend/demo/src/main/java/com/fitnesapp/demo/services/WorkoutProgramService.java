package com.fitnesapp.demo.services;

import com.fitnesapp.demo.models.WorkoutProgram;
import com.fitnesapp.demo.repositories.WorkoutProgramRepository;
import org.springframework.stereotype.Service;

@Service
public class WorkoutProgramService {
    private final WorkoutProgramRepository workoutProgramRepository;

    public WorkoutProgramService(WorkoutProgramRepository workoutProgramRepository) {
        this.workoutProgramRepository = workoutProgramRepository;
    }

    public WorkoutProgram saveWorkoutProgram(WorkoutProgram program) {
        return workoutProgramRepository.save(program);
    }
} 