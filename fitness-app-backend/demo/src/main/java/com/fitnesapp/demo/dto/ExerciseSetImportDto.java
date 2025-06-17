package com.fitnesapp.demo.dto;

import lombok.Data;

@Data
public class ExerciseSetImportDto {
    private int setNo;
    private Integer reps;
    private Double weight;
    private Integer rpe;
    private Integer durationSec;
} 