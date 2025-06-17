package com.fitnesapp.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseDto {
    private String name;
    private String type;
    private String muscleGroup;
    private String imageUrl;
    private String duration;
    private String calories;
    private Integer sets;
    private Integer reps;
    private List<String> instructions;
    private List<String> tips;
}
