package com.fitnesapp.demo.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class UserWorkoutProgramResponseDto {
    private Long id;
    private Long workoutProgramId;
    private LocalDate startDate;
    private List<UserWorkoutDayResponseDto> savedDays;
} 