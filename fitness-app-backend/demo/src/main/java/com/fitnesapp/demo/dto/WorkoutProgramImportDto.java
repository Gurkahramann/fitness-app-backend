package com.fitnesapp.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class WorkoutProgramImportDto {
    private String title;
    private String slug;
    private String description;
    private String difficulty;
    private int durationWeeks;
    private String coverImageUrl;
    private String thumbnailUrl;
    private List<String> tags;
    private List<Long> exercises; // List of exercise IDs
    private List<WorkoutDayImportDto> days;

} 