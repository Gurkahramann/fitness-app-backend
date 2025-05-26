package com.fitnesapp.demo.models;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "workout_day_exercises")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkoutDayExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "day_id")
    private WorkoutDay day;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    private Integer sets;
    private Integer reps;
    private Integer durationSec;
    private Integer restSec;
}