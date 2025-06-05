package com.fitnesapp.demo.dto;

import lombok.Data;

@Data
public class WorkoutDayRequestDto {
    private String userId;
    private String date; // YYYY-MM-DD
    private Long exerciseId;
}