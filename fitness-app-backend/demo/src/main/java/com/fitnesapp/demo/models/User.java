package com.fitnesapp.demo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Document(collection = "users") // 🔹 MongoDB Koleksiyonu
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id; 
    private String email;
    private String name;
    private String password;
    private String role;
    private String gender;
    private Double height;
    private Double weight;
    private Integer age;
    private String activityLevel;
    private String goal;
    private String birthDate; // YYYY-MM-DD formatında saklanacak
}
