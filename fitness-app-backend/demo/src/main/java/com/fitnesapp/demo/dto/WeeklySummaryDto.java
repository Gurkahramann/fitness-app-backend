package com.fitnesapp.demo.dto;

import lombok.Data;

@Data
public class WeeklySummaryDto {
    private String userId;
    private String weekStartDate; // ISO format (örn: 2024-05-13)
    private int totalWorkouts;
    private int totalCalories;
    private int totalDuration;
}
