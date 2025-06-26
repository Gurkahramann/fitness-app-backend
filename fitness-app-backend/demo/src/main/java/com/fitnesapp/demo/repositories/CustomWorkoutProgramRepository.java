package com.fitnesapp.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fitnesapp.demo.models.CustomWorkoutProgram;

public interface CustomWorkoutProgramRepository extends JpaRepository<CustomWorkoutProgram, Long> {
    List<CustomWorkoutProgram> findByUserId(String userId);
}