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
    private String primaryMuscle;     // "Chest"
    private String equipment;         // "Barbell"
    private String mediaUrl;          // Media URL (image/video thumbnail)
    
    @Lob
    private String description;
} 