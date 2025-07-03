package com.fitnesapp.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.fitnesapp.demo.models.ExerciseLog;

public interface ExerciseLogRepository extends JpaRepository<ExerciseLog, Long> {
    List<ExerciseLog> findByUserIdAndDateBetween(String userId, String start, String end);
    List<ExerciseLog> findByUserIdAndDateBetweenAndExerciseId(String userId, String start, String end, String exerciseId);
}
