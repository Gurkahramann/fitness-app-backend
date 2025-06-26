package com.fitnesapp.demo.dto;
import lombok.Data;

@Data
public class ExerciseLogDto {
    private String userId;
    private String exerciseId;
    private String date;
    private int durationSeconds;
}
