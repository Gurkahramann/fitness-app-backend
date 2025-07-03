package com.fitnesapp.demo.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodLogDto {
    private Long id;
    private String userId;
    private String foodName;
    private Double estimatedGrams;
    private Double totalCalories;
    private Double caloriesPer100g;
    private Double proteinPer100g;
    private Double carbsPer100g;
    private Double fatPer100g;
    private LocalDateTime createdAt;

} 