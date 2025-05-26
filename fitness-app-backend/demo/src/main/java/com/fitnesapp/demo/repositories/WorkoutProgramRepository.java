package com.fitnesapp.demo.repositories;

import com.fitnesapp.demo.models.WorkoutProgram;
import org.springframework.data.jpa.repository.JpaRepository;   // veya Mongo kullanıyorsanız MongoRepository
import org.springframework.stereotype.Repository;
@Repository
public interface WorkoutProgramRepository extends JpaRepository<WorkoutProgram, Long> {
    // Ek sorgular eklenebilir
} 