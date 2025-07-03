package com.fitnesapp.demo.services;

import com.fitnesapp.demo.dto.UserWorkoutProgramDto;
import com.fitnesapp.demo.dto.UserWorkoutProgramResponseDto;
import com.fitnesapp.demo.dto.UserWorkoutDayResponseDto;
import com.fitnesapp.demo.dto.ExerciseDto;
import com.fitnesapp.demo.dto.UserExerciseEntryResponseDto;
import com.fitnesapp.demo.models.UserWorkoutProgram;
import com.fitnesapp.demo.models.UserWorkoutDay;
import com.fitnesapp.demo.models.Exercise;
import com.fitnesapp.demo.models.UserExerciseEntry;
import com.fitnesapp.demo.repositories.UserWorkoutProgramRepository;
import com.fitnesapp.demo.repositories.ExerciseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.util.List;

@Service
public class UserWorkoutProgramService {

    @Autowired
    private UserWorkoutProgramRepository userWorkoutProgramRepository;
    @Autowired
    private ExerciseRepository exerciseRepository;
    @Transactional
    public String saveUserWorkoutProgram(UserWorkoutProgramDto dto) {
        UserWorkoutProgram program = UserWorkoutProgram.builder()
            .userId(dto.getUserId())
            .startDate(dto.getStartDate())
            .workoutProgramId(dto.getWorkoutProgramId())
            .customWorkoutProgramId(dto.getCustomWorkoutProgramId())
            .build();

        // Save workout days
        if (dto.getSavedDays() != null) {
            program.setSavedDays(dto.getSavedDays().stream()
                .map(dayDto -> {
                    UserWorkoutDay day = UserWorkoutDay.builder()
                        .dayNumber(dayDto.getDayNumber())
                        .userWorkoutProgram(program)
                        .build();

                    // Save exercise entries
                    if (dayDto.getExerciseEntries() != null) {
                        day.setSavedExerciseEntries(dayDto.getExerciseEntries().stream()
                            .map(exerciseDto -> UserExerciseEntry.builder()
                                .exerciseId(exerciseDto.getExerciseId())
                                .orderIndex(exerciseDto.getOrderIndex())
                                .sets(exerciseDto.getSets())
                                .reps(exerciseDto.getReps())
                                .weight(exerciseDto.getWeight())
                                .duration(exerciseDto.getDuration())
                                .userWorkoutDay(day)
                                .build())
                            .collect(Collectors.toList()));
                    }

                    return day;
                })
                .collect(Collectors.toList()));
        }

        userWorkoutProgramRepository.save(program);
        return "Program başarıyla kaydedildi!";
    }

    public List<UserWorkoutProgramResponseDto> getUserProgramsWithDetails(String userId) {
        List<UserWorkoutProgram> programs = userWorkoutProgramRepository.findByUserId(userId);
        return programs.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private UserWorkoutProgramResponseDto convertToDto(UserWorkoutProgram program) {
        UserWorkoutProgramResponseDto dto = new UserWorkoutProgramResponseDto();
        dto.setId(program.getId());
        dto.setUserId(program.getUserId());
        dto.setWorkoutProgramId(program.getWorkoutProgramId());
        dto.setCustomWorkoutProgramId(program.getCustomWorkoutProgramId());
        dto.setStartDate(program.getStartDate());

        if (program.getSavedDays() != null) {
            dto.setSavedDays(program.getSavedDays().stream()
                .map(this::convertDayToDto)
                .collect(Collectors.toList()));
        }
        return dto;
    }

    private UserWorkoutDayResponseDto convertDayToDto(UserWorkoutDay day) {
        UserWorkoutDayResponseDto dto = new UserWorkoutDayResponseDto();
        dto.setId(day.getId());
        dto.setDayNumber(day.getDayNumber());
        if (day.getSavedExerciseEntries() != null) {
            dto.setExerciseEntries(day.getSavedExerciseEntries().stream().map(this::toEntryResponseDto).collect(java.util.stream.Collectors.toList()));
        }
        return dto;
    }

    private UserExerciseEntryResponseDto toEntryResponseDto(UserExerciseEntry entry) {
        UserExerciseEntryResponseDto dto = new UserExerciseEntryResponseDto();
        dto.setId(entry.getId());
        dto.setExerciseId(entry.getExerciseId());
        dto.setOrderIndex(entry.getOrderIndex());
        dto.setSets(entry.getSets());
        dto.setReps(entry.getReps());
        dto.setWeight(entry.getWeight());
        dto.setDuration(entry.getDuration());
        Exercise exercise = exerciseRepository.findById(entry.getExerciseId()).orElse(null);
        if (exercise != null) {
            ExerciseDto exerciseDto = new ExerciseDto();
            exerciseDto.setName(exercise.getName());
            exerciseDto.setType(exercise.getType());
            exerciseDto.setMuscleGroup(exercise.getMuscleGroup());
            exerciseDto.setImageUrl(exercise.getImageUrl());
            dto.setExercise(exerciseDto);
            exerciseDto.setDuration(exercise.getDuration());
            exerciseDto.setCalories(exercise.getCalories());
            exerciseDto.setSets(exercise.getSets());
            exerciseDto.setReps(exercise.getReps());
            exerciseDto.setInstructions(exercise.getInstructions());
            exerciseDto.setTips(exercise.getTips());
            dto.setExercise(exerciseDto);
        }
        return dto;
    }

    @Transactional
    public void deleteUserWorkoutProgram(String userId, String programId) {
        List<UserWorkoutProgram> userPrograms = userWorkoutProgramRepository.findByUserId(userId);
        UserWorkoutProgram program = userPrograms.stream()
            .filter(p -> p.getId().toString().equals(programId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Program bulunamadı"));
        
        userWorkoutProgramRepository.delete(program);
    }
} 