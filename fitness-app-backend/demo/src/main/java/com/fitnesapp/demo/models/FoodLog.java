package com.fitnesapp.demo.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor 
@Table(name = "food_logs")
public class FoodLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String foodName;

    @Column(nullable = false)
    private Double estimatedGrams;

    @Column(nullable = false)
    private Double totalCalories;

    @Column(nullable = false)
    private Double caloriesPer100g;

    @Column(nullable = false)
    private Double proteinPer100g;

    @Column(nullable = false)
    private Double carbsPer100g;

    @Column(nullable = false)
    private Double fatPer100g;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

} 