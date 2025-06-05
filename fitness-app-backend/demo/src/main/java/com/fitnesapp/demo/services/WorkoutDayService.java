package com.fitnesapp.demo.services;

import com.fitnesapp.demo.models.*;
import com.fitnesapp.demo.repositories.UserRepository;
import com.fitnesapp.demo.repositories.WorkoutDayRepository;
import com.fitnesapp.demo.repositories.ExerciseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;

@Service
public class WorkoutDayService {

    private final UserRepository userRepository;
    private final WorkoutDayRepository workoutDayRepository;
    private final ExerciseRepository exerciseRepository;

    public WorkoutDayService(WorkoutDayRepository workoutDayRepository, ExerciseRepository exerciseRepository, UserRepository userRepository) {
        this.workoutDayRepository = workoutDayRepository;
        this.exerciseRepository = exerciseRepository;
        this.userRepository = userRepository;
    }
    public User getUserForWorkoutDay(WorkoutDay workoutDay) {
        return userRepository.findById(workoutDay.getUserId()).orElse(null);
    }
    @Transactional
    public WorkoutDay createWorkoutDayWithExercise(String userId, String date, Long exerciseId) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));

        WorkoutDay workoutDay = WorkoutDay.builder()
                .userId(userId)
                .date(date)
                .build();

        ExerciseEntry entry = ExerciseEntry.builder()
                .workoutDay(workoutDay)
                .exercise(exercise)
                .orderIndex(1)
                .build();

        workoutDay.setExerciseEntries(Collections.singletonList(entry));
        return workoutDayRepository.save(workoutDay);
    }
} 