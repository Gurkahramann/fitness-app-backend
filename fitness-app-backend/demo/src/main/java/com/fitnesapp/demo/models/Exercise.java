package com.fitnesapp.demo.models;

import jakarta.persistence.*;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type; // "cardio" veya "strength"

    private String muscleGroup;

    // videoUrl kaldırıldı. imageUrl .gif'leri tutacak.
    private String imageUrl;

    @ElementCollection // Basit bir liste için en iyi yöntem
    @CollectionTable(name = "exercise_instructions", joinColumns = @JoinColumn(name = "exercise_id"))
    @Column(name = "instruction", length = 512)
    private List<String> instructions;

    @ElementCollection
    @CollectionTable(name = "exercise_tips", joinColumns = @JoinColumn(name = "exercise_id"))
    @Column(name = "tip", length = 512)
    private List<String> tips;

    private String duration; // "3 sets x 10-15 reps" gibi metinsel bilgi
    private String calories; // "5-8 cal/min" gibi metinsel bilgi

    private Integer sets;
    private Integer reps;

    // Getters and Setters
}