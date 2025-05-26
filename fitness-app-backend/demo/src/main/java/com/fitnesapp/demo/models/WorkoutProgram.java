package com.fitnesapp.demo.models;

import lombok.*;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "workout_programs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkoutProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(unique = true, length = 120)
    private String slug;

    @Lob
    private String description;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    private Integer durationWeeks;
    private String coverImageUrl;
    private String thumbnailUrl;

    @ElementCollection
    @CollectionTable(name = "workout_program_tags", joinColumns = @JoinColumn(name = "program_id"))
    @Column(name = "tag")
    private Set<String> tags;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("dayIndex ASC")
    private List<WorkoutDay> days;

    // Sosyal alanlar
    private String createdBy; // User id (veya @ManyToOne ile User entity'si)
    
    @ElementCollection
    @CollectionTable(name = "program_likes", joinColumns = @JoinColumn(name = "program_id"))
    @Column(name = "user_id")
    private Set<String> likes;
    @Builder.Default
    private boolean isPublic = false;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Exercise> exercises;
    @CreationTimestamp private Instant createdAt;
    @UpdateTimestamp   private Instant updatedAt;
}