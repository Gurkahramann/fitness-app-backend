package com.fitnesapp.demo.models;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "user_workout_programs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserWorkoutProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private Long workoutProgramId;

    @Column(nullable = false)
    private LocalDate startDate;

    @OneToMany(mappedBy = "userWorkoutProgram", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserWorkoutDay> savedDays;
} 