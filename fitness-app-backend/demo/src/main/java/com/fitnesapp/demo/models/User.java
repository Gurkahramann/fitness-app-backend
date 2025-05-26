package com.fitnesapp.demo.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
