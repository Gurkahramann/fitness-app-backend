package com.fitnesapp.demo.controller;

import com.fitnesapp.demo.dto.ExerciseLogDto;
import com.fitnesapp.demo.dto.WeeklySummaryDto;
import com.fitnesapp.demo.dto.WeeklyWorkoutHistoryItemDto;
import com.fitnesapp.demo.services.ExerciseLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/exercise-log")
public class ExerciseLogController {
    @Autowired
    private ExerciseLogService exerciseLogService;

    @PostMapping
    public ResponseEntity<?> logExercise(@RequestBody ExerciseLogDto dto) {
        exerciseLogService.saveLog(dto);
        return ResponseEntity.ok().build();
    }   
    @GetMapping("/weekly-summary")
    public ResponseEntity<WeeklySummaryDto> getWeeklySummary(
        @RequestParam String userId,
        @RequestParam String weekStart // ISO format: yyyy-MM-dd
    ) {
        LocalDate start = LocalDate.parse(weekStart);
        LocalDate end = start.plusDays(6);
        WeeklySummaryDto summary = exerciseLogService.getWeeklySummary(userId, start, end);
        return ResponseEntity.ok(summary);
    }
    @GetMapping("/weekly-history")
    public ResponseEntity<List<WeeklyWorkoutHistoryItemDto>> getWeeklyHistory(
        @RequestParam String userId,
        @RequestParam String weekStart // yyyy-MM-dd
    ) {
        LocalDate start = LocalDate.parse(weekStart);
        LocalDate end = start.plusDays(6);
        List<WeeklyWorkoutHistoryItemDto> history = exerciseLogService.getWeeklyWorkoutHistory(userId, start, end);
        return ResponseEntity.ok(history);
    }
}