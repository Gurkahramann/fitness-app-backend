package com.fitnesapp.demo.dto;

import java.util.List;

import com.fitnesapp.demo.models.Exercise;

public class WorkoutProgramDto {
    public Long id;
    public String title;
    public String slug;
    public String description;
    public String difficulty;
    public List<String> tags;
    public int durationWeeks;
    public List<WorkoutDayDto> days;
    public List<Exercise> exercises;
    public String coverImageUrl;
}