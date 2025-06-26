package com.fitnesapp.demo.services;

import com.fitnesapp.demo.dto.ExerciseLogDto;
import com.fitnesapp.demo.dto.WeeklySummaryDto;
import com.fitnesapp.demo.dto.WeeklyWorkoutHistoryItemDto;
import com.fitnesapp.demo.models.Exercise;
import com.fitnesapp.demo.models.ExerciseLog;
import com.fitnesapp.demo.models.User;
import com.fitnesapp.demo.repositories.ExerciseLogRepository;
import com.fitnesapp.demo.repositories.ExerciseRepository;
import com.fitnesapp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
    
@Service
public class ExerciseLogService {
    @Autowired
    private ExerciseLogRepository exerciseLogRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    public void saveLog(ExerciseLogDto dto) {
        ExerciseLog log = new ExerciseLog();
        log.setUserId(dto.getUserId());
        log.setExerciseId(dto.getExerciseId());
        log.setDate(dto.getDate());
        log.setDurationSeconds(dto.getDurationSeconds());

        // Kullanıcı bilgilerini çek
        User user = userRepository.findById(dto.getUserId()).orElse(null);

        double weight = user != null ? user.getWeight() : 70.0; // kg
        String gender = user != null ? user.getGender() : "male";

        // Egzersiz türüne göre MET değeri belirle (örnek: cardio/strength)
        double baseMet = 7.0; // default cardio

        // Cinsiyete göre MET ayarı
        double met = baseMet;
        if (gender.equalsIgnoreCase("male")) {
            met += 0.5;
        } else if (gender.equalsIgnoreCase("female")) {
            met -= 0.5;
        }

        double hours = dto.getDurationSeconds() / 3600.0;

        // Kalori hesabı
        double calories = met * weight * hours;

        log.setCalories(calories);

        exerciseLogRepository.save(log);
    }
    public WeeklySummaryDto getWeeklySummary(String userId, LocalDate weekStart, LocalDate weekEnd) {
        List<ExerciseLog> logs = exerciseLogRepository.findByUserIdAndDateBetween(userId, weekStart.toString(), weekEnd.toString());
        int totalCalories = 0;
        int totalDuration = 0;
        Set<LocalDate> workoutDays = new HashSet<>();
        for (ExerciseLog log : logs) {
            totalCalories += (int) log.getCalories();
            totalDuration += log.getDurationSeconds();
            workoutDays.add(LocalDate.parse(log.getDate()));
        }
        WeeklySummaryDto summary = new WeeklySummaryDto();
        summary.setUserId(userId);
        summary.setWeekStartDate(weekStart.toString());
        summary.setTotalWorkouts(workoutDays.size());
        summary.setTotalCalories(totalCalories);
        summary.setTotalDuration(totalDuration);
        return summary;
    }
    public List<WeeklyWorkoutHistoryItemDto> getWeeklyWorkoutHistory(String userId, LocalDate weekStart, LocalDate weekEnd) {
        List<ExerciseLog> logs = exerciseLogRepository.findByUserIdAndDateBetween(userId, weekStart.toString(), weekEnd.toString());
        return logs.stream().map(log -> {
            WeeklyWorkoutHistoryItemDto dto = new WeeklyWorkoutHistoryItemDto();
            dto.setDate(log.getDate().toString());
            dto.setDurationMinutes(log.getDurationSeconds() / 60);
            dto.setCalories((int) log.getCalories());
            // Egzersiz adını ve tipini bulmak için:
            Exercise exercise = null;
            try {
                exercise = exerciseRepository.findById(Long.parseLong(log.getExerciseId())).orElse(null);
            } catch (Exception e) {
                // log veya boş bırak
            }
            dto.setExerciseName(exercise != null ? exercise.getName() : "");
            dto.setExerciseType(exercise != null ? exercise.getType() : "");
            return dto;
        }).collect(Collectors.toList());
    }
}