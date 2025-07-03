package com.fitnesapp.demo.models;

import lombok.*;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "custom_exercise_entries")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomExerciseEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_workout_day_id", nullable = false)
    @JsonBackReference
    private CustomWorkoutDay customWorkoutDay;

    private int orderIndex;

    private Long exerciseId; // Referans olarak sadece id tutuyoruz

    private Integer sets;
    private Integer reps;
    private Double weight;
    private String duration;
}