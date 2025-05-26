package com.fitnesapp.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    private String gender;
    private double height;
    private double weight;
    private int age;
    private String activityLevel;
    private String goal;
    private String birthDate;
}
