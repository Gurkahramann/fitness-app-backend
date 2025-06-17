package com.fitnesapp.demo.repositories;

import com.fitnesapp.demo.models.WorkoutProgram;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;   // veya Mongo kullanıyorsanız MongoRepository
import org.springframework.stereotype.Repository;
@Repository
public interface WorkoutProgramRepository extends JpaRepository<WorkoutProgram, Long> {
    Optional<WorkoutProgram> findBySlug(String slug);
    List<WorkoutProgram> findAll();
} 