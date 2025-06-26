package com.fitnesapp.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class CustomWorkoutDayDto {
    private int dayOfWeek;
    private List<CustomExerciseEntryDto> exerciseEntries;
}