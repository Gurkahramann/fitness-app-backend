package com.fitnesapp.demo.dto;

import lombok.Data;

@Data
public class WeeklyWorkoutHistoryItemDto {
    private String exerciseName;
    private String exerciseType;
    private int durationMinutes;
    private int durationSeconds;
    private int calories;
    private String date;
    private boolean completed;
}
