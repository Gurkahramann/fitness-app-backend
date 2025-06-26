package com.fitnesapp.demo.repositories;

import com.fitnesapp.demo.models.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Optional<Exercise> findById(Long id);
} 