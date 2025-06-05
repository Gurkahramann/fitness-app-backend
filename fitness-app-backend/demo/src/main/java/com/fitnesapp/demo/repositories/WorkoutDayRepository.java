package com.fitnesapp.demo.repositories;

import com.fitnesapp.demo.models.WorkoutDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutDayRepository extends JpaRepository<WorkoutDay, Long> {
} 