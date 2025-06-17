package com.fitnesapp.demo.services;

import com.fitnesapp.demo.models.Exercise;
import com.fitnesapp.demo.repositories.ExerciseRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public Exercise createExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }
    // ExerciseService.saveAll
    public List<Exercise> saveAll(List<Exercise> list) {
        list.forEach(e -> {
            if (e.getName() == null)
                System.out.println("NAME IS NULL! Exercise JSON id=" + e.getId());
        });
        return exerciseRepository.saveAll(list);
    }
    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

}