package com.fitnesapp.demo.models;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "exercise_sets")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExerciseSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_entry_id")
    private ExerciseEntry exerciseEntry;

    private Integer setNo;
    private Integer reps;
    private Double weight;
    private Integer rpe;
    private Integer durationSec;
}
