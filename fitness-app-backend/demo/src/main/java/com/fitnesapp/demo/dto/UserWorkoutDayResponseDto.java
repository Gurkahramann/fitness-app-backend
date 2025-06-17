package com.fitnesapp.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserWorkoutDayResponseDto {
    private Long id;
    private int dayNumber;
    private List<UserExerciseEntryResponseDto> exerciseEntries;
} 