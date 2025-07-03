package com.fitnesapp.demo.dto;

import lombok.Data;

@Data
public class CustomExerciseEntryDto {
    private int orderIndex;
    private Long exerciseId;
    private Integer sets;
    private Integer reps;
    private Double weight;
    private String duration;
}
