package com.fitnesapp.demo.models;

import lombok.*;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

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
    @JoinColumn(name = "exercise_entry_id", nullable = false)
    @JsonBackReference
    private ExerciseEntry exerciseEntry;

    private int setNo;
    private Integer reps;
    private Double weight;
    private Integer rpe; // Zorluk derecesi (Rating of Perceived Exertion)
    private Integer durationSec;

    // Getters and Setters
}
