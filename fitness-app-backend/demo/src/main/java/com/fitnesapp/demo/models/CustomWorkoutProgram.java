package com.fitnesapp.demo.models;

import lombok.*;
import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "custom_workout_programs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomWorkoutProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String title;

    @Column(length = 1024)
    private String description;

    private int durationWeeks;

    @ElementCollection
    private List<String> tags;

    private String coverImageUrl;

    @OneToMany(mappedBy = "customWorkoutProgram", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CustomWorkoutDay> days;
}