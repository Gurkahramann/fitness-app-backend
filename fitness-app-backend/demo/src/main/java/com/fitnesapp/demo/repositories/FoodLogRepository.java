package com.fitnesapp.demo.repositories;

import com.fitnesapp.demo.models.FoodLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FoodLogRepository extends JpaRepository<FoodLog, Long> {
    List<FoodLog> findAllByUserId(String userId);
    List<FoodLog> findAllByUserIdOrderByCreatedAtDesc(String userId);
} 