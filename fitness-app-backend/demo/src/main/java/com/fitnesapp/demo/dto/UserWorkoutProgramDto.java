package com.fitnesapp.demo.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserWorkoutProgramDto {
    private String userId;
    private Long workoutProgramId;
    private LocalDate startDate;
    private List<UserWorkoutDayDto> savedDays;
} 