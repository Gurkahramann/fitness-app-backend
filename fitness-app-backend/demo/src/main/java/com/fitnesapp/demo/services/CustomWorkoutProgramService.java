package com.fitnesapp.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import com.fitnesapp.demo.repositories.UserWorkoutProgramRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitnesapp.demo.dto.CustomWorkoutProgramDto;
import com.fitnesapp.demo.models.CustomWorkoutDay;
import com.fitnesapp.demo.models.CustomExerciseEntry;
import com.fitnesapp.demo.models.CustomWorkoutProgram;
import com.fitnesapp.demo.repositories.CustomWorkoutProgramRepository;

@Service
public class CustomWorkoutProgramService {

    @Autowired
    private CustomWorkoutProgramRepository customWorkoutProgramRepository;

    @Autowired
    private UserWorkoutProgramRepository userWorkoutProgramRepository;

    public CustomWorkoutProgram saveCustomProgram(CustomWorkoutProgramDto dto) {
        CustomWorkoutProgram program = new CustomWorkoutProgram();
        program.setUserId(dto.getUserId());
        program.setTitle(dto.getTitle());
        program.setDescription(dto.getDescription());
        program.setDurationWeeks(dto.getDurationWeeks());
        program.setTags(dto.getTags());
        program.setCoverImageUrl(dto.getCoverImageUrl());

        if (dto.getDays() != null) {
            List<CustomWorkoutDay> days = dto.getDays().stream().map(dayDto -> {
                CustomWorkoutDay day = new CustomWorkoutDay();
                day.setDayOfWeek(dayDto.getDayOfWeek());
                day.setCustomWorkoutProgram(program);
                if (dayDto.getExerciseEntries() != null) {
                    List<CustomExerciseEntry> entries = dayDto.getExerciseEntries().stream().map(entryDto -> {
                        CustomExerciseEntry entry = new CustomExerciseEntry();
                        entry.setOrderIndex(entryDto.getOrderIndex());
                        entry.setExerciseId(entryDto.getExerciseId());
                        entry.setCustomWorkoutDay(day);
                        return entry;
                    }).collect(Collectors.toList());
                    day.setExerciseEntries(entries);
                }
                return day;
            }).collect(Collectors.toList());
            program.setDays(days);
        }
        return customWorkoutProgramRepository.save(program);
    }

    public List<CustomWorkoutProgram> getUserPrograms(String userId) {
        return customWorkoutProgramRepository.findByUserId(userId);
    }
    @Transactional
    public void deleteCustomProgram(Long id, String userId) {
        CustomWorkoutProgram program = customWorkoutProgramRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Program not found"));
        if (!program.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        // Delete associated user workout programs
        userWorkoutProgramRepository.deleteByCustomWorkoutProgramId(id);

        customWorkoutProgramRepository.deleteById(id);
    }
}
