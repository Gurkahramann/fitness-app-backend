package com.fitnesapp.demo.models;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "exercises")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;              // "Bench Press"
    private String type;              // "cardio" or "strength"
    private String muscleGroup;       // "Chest"
    private String videoUrl;          // Video URL
    @ElementCollection
    private java.util.List<String> instructions;
    @ElementCollection
    private java.util.List<String> tips;
    private String duration;
    private String calories;
    private String image;
} 