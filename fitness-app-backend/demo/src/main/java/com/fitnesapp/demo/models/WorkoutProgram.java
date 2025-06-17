package com.fitnesapp.demo.models;

import lombok.*;
import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "workout_programs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkoutProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(length = 1024)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    @Column(nullable = false)
    private List<String> tags;

    private int durationWeeks;

    @OneToMany(mappedBy = "workoutProgram", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<WorkoutDay> days;

    @ManyToMany
    @JoinTable(
      name = "program_exercises", 
      joinColumns = @JoinColumn(name = "program_id"), 
      inverseJoinColumns = @JoinColumn(name = "exercise_id"))
    private List<Exercise> exercises;

    private String coverImageUrl;

}