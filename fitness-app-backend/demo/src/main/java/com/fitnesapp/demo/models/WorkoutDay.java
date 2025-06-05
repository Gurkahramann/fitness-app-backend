package com.fitnesapp.demo.models;

import lombok.*;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "workout_days")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkoutDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId; // MongoDB'deki User'ın id'si

    private String date; // YYYY-MM-DD

    @OneToMany(mappedBy = "workoutDay", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExerciseEntry> exerciseEntries;
}