package com.fitnesapp.demo.dto;

import com.fitnesapp.demo.dto.ExerciseDto;

import lombok.Data;

@Data
public class UserExerciseEntryResponseDto {
    private Long id;
    private Long exerciseId;
    private int orderIndex;
    private ExerciseDto exercise;
    private Integer sets;
    private Integer reps;
    private Double weight;
    private String duration;
} 