package com.fitnesapp.demo.models;

import lombok.*;
import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "user_workout_days")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserWorkoutDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_workout_program_id", nullable = false)
    @JsonBackReference
    private UserWorkoutProgram userWorkoutProgram;

    private int dayNumber; // 1=Pazartesi, ..., 7=Pazar

    @OneToMany(mappedBy = "userWorkoutDay", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC")
    private List<UserExerciseEntry> savedExerciseEntries;
} 