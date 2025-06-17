package com.fitnesapp.demo.services;

import com.fitnesapp.demo.models.WorkoutProgram;
import com.fitnesapp.demo.repositories.WorkoutProgramRepository;
import com.fitnesapp.demo.dto.WorkoutProgramDto;
import com.fitnesapp.demo.dto.WorkoutDayDto;
import com.fitnesapp.demo.dto.ExerciseEntryDto;
import com.fitnesapp.demo.dto.ExerciseSetDto;
import com.fitnesapp.demo.models.ExerciseSet;
import com.fitnesapp.demo.models.ExerciseEntry;
import com.fitnesapp.demo.models.WorkoutDay;
import com.fitnesapp.demo.models.Exercise;
import com.fitnesapp.demo.dto.WorkoutProgramImportDto;
import com.fitnesapp.demo.dto.WorkoutDayImportDto;
import com.fitnesapp.demo.dto.ExerciseEntryImportDto;
import com.fitnesapp.demo.dto.ExerciseSetImportDto;
import com.fitnesapp.demo.repositories.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkoutProgramService {
    private final WorkoutProgramRepository workoutProgramRepository;
    @Autowired
    private ExerciseRepository exerciseRepository;

    public WorkoutProgramService(WorkoutProgramRepository workoutProgramRepository) {
        this.workoutProgramRepository = workoutProgramRepository;
    }

    public WorkoutProgram saveWorkoutProgram(WorkoutProgram program) {
        return workoutProgramRepository.save(program);
    }

    // WorkoutProgramRepository içindeki findAll() metodunu kullanarak tüm WorkoutProgram'ları çeken servis metodu
    public List<WorkoutProgram> getAllWorkoutPrograms() {
        return workoutProgramRepository.findAll();
    }

    public List<WorkoutProgramDto> getAllWorkoutProgramDtos() {
        List<WorkoutProgram> programs = workoutProgramRepository.findAll();
        return programs.stream().map(this::toDto).collect(Collectors.toList());
    }

    private WorkoutProgramDto toDto(WorkoutProgram program) {
        WorkoutProgramDto dto = new WorkoutProgramDto();
        dto.id = program.getId();
        dto.title = program.getTitle();
        dto.slug = program.getSlug();
        dto.description = program.getDescription();
        dto.difficulty = program.getDifficulty().toString();
        dto.tags = program.getTags();
        dto.durationWeeks = program.getDurationWeeks();
        dto.exercises = program.getExercises();
        dto.coverImageUrl = program.getCoverImageUrl();
        dto.days = program.getDays() != null ? program.getDays().stream().map(this::toDayDto).collect(Collectors.toList()) : null;
        return dto;
    }

    private WorkoutDayDto toDayDto(WorkoutDay day) {
        WorkoutDayDto dto = new WorkoutDayDto();
        dto.dayOfWeek = day.getDayOfWeek();
        dto.exerciseEntries = day.getExerciseEntries() != null ? day.getExerciseEntries().stream().map(this::toEntryDto).collect(Collectors.toList()) : null;
        return dto;
    }

    private ExerciseEntryDto toEntryDto(ExerciseEntry entry) {
        ExerciseEntryDto dto = new ExerciseEntryDto();
        dto.orderIndex = entry.getOrderIndex();
        dto.exerciseId = entry.getExercise() != null ? entry.getExercise().getId() : null;
        //dto.exerciseSets = entry.getExerciseSets() != null ? entry.getExerciseSets().stream().map(this::toSetDto).collect(Collectors.toList()) : null;
        return dto;
    }
    public WorkoutProgramDto getWorkoutProgramDtoById(Long id) {
        WorkoutProgram program = workoutProgramRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Program not found"));
        return toDto(program);
    }
    private ExerciseSetDto toSetDto(ExerciseSet set) {
        ExerciseSetDto dto = new ExerciseSetDto();
        dto.setNo = set.getSetNo();
        dto.reps = set.getReps();
        dto.weight = set.getWeight();
        dto.rpe = set.getRpe();
        dto.durationSec = set.getDurationSec();
        return dto;
    }

    @Transactional
    public int importWorkoutPrograms(List<WorkoutProgramImportDto> dtos) {
        int count = 0;
        for (WorkoutProgramImportDto dto : dtos) {
            WorkoutProgram program = new WorkoutProgram();
            program.setTitle(dto.getTitle());
            program.setSlug(dto.getSlug());
            program.setDescription(dto.getDescription());
            program.setDifficulty(com.fitnesapp.demo.models.Difficulty.valueOf(dto.getDifficulty()));
            program.setDurationWeeks(dto.getDurationWeeks());
            program.setCoverImageUrl(dto.getCoverImageUrl());
            program.setTags(dto.getTags());
            // Map exercises by ID
            if (dto.getExercises() != null) {
                List<Exercise> exercises = dto.getExercises().stream()
                        .map(id -> exerciseRepository.findById(id)
                            .orElseThrow(() -> new IllegalArgumentException("Exercise not found for id: " + id)))
                        .collect(Collectors.toList());
                program.setExercises(exercises);
            }
            // Map days
            if (dto.getDays() != null) {
                List<WorkoutDay> days = dto.getDays().stream().map(dayDto -> {
                    WorkoutDay day = new WorkoutDay();
                    day.setDayOfWeek(dayDto.getDayOfWeek());
                    day.setWorkoutProgram(program);
                    // Map exercise entries
                    if (dayDto.getExerciseEntries() != null) {
                        List<ExerciseEntry> entries = dayDto.getExerciseEntries().stream().map(entryDto -> {
                            ExerciseEntry entry = new ExerciseEntry();
                            entry.setOrderIndex(entryDto.getOrderIndex());
                            entry.setWorkoutDay(day);
                            // Set exercise by id (throw if not found)
                            if (entryDto.getExerciseId() != null) {
                                Exercise exercise = exerciseRepository.findById(entryDto.getExerciseId())
                                    .orElseThrow(() -> new IllegalArgumentException("Exercise not found for id: " + entryDto.getExerciseId()));
                                entry.setExercise(exercise);
                            } else {
                                throw new IllegalArgumentException("ExerciseId is null in exerciseEntry!");
                            }
                            // Map sets
                            if (entryDto.getExerciseSets() != null) {
                                List<ExerciseSet> sets = entryDto.getExerciseSets().stream().map(setDto -> {
                                    ExerciseSet set = new ExerciseSet();
                                    set.setSetNo(setDto.getSetNo());
                                    set.setReps(setDto.getReps());
                                    set.setWeight(setDto.getWeight());
                                    set.setRpe(setDto.getRpe());
                                    set.setDurationSec(setDto.getDurationSec());
                                    set.setExerciseEntry(entry);
                                    return set;
                                }).collect(Collectors.toList());
                                entry.setExerciseSets(sets);
                            }
                            return entry;
                        }).collect(Collectors.toList());
                        day.setExerciseEntries(entries);
                    }
                    return day;
                }).collect(Collectors.toList());
                program.setDays(days);
            }
            workoutProgramRepository.save(program);
            count++;
        }
        return count;
    }
} 