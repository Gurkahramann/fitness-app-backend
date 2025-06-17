package com.fitnesapp.demo.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserExerciseEntryDto {
    private Long exerciseId;
    private int orderIndex;
} 