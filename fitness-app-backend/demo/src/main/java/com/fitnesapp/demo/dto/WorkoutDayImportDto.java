package com.fitnesapp.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class WorkoutDayImportDto {
    private String userId;
    private String date;
    private int dayOfWeek;
    private List<ExerciseEntryImportDto> exerciseEntries;
} 