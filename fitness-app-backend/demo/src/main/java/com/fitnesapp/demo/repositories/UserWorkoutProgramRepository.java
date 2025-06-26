package com.fitnesapp.demo.repositories;

import com.fitnesapp.demo.models.UserWorkoutProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWorkoutProgramRepository extends JpaRepository<UserWorkoutProgram, Long> {
    // Kullanıcıya ait programları getir
    java.util.List<UserWorkoutProgram> findByUserId(String userId);

    void deleteByCustomWorkoutProgramId(Long customWorkoutProgramId);
} 