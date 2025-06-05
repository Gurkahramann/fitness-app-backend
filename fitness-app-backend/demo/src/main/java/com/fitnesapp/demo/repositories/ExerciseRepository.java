package com.fitnesapp.demo.repositories;

import com.fitnesapp.demo.models.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
} 