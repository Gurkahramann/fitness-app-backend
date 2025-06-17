package com.fitnesapp.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class ExerciseEntryImportDto {
    private int orderIndex;
    private Long exerciseId;
    private List<ExerciseSetImportDto> exerciseSets;
} 