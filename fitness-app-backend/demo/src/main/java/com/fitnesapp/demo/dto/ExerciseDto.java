package com.fitnesapp.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseDto {
    private String name;
    private String type;
    private String muscleGroup;
    private String videoUrl;
}
