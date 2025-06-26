package com.fitnesapp.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class CustomWorkoutProgramDto {
    private Long id;
    private String userId;
    private String title;   
    private String description;
    private int durationWeeks;
    private List<String> tags;
    private String coverImageUrl;
    private List<CustomWorkoutDayDto> days;
}

