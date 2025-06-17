package com.fitnesapp.demo.dto;

import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserWorkoutDayDto {
    private int dayNumber; // 1=Pazartesi, ..., 7=Pazar
    private List<UserExerciseEntryDto> exerciseEntries;
} 