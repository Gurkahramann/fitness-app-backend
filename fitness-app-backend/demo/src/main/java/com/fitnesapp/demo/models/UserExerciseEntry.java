package com.fitnesapp.demo.models;

import lombok.*;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "user_exercise_entries")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserExerciseEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_workout_day_id", nullable = false)
    @JsonBackReference
    private UserWorkoutDay userWorkoutDay;

    @Column(nullable = false)
    private Long exerciseId;

    @Column(nullable = false)
    private int orderIndex;
} 