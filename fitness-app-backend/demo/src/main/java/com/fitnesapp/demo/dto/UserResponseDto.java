package com.fitnesapp.demo.dto;
import com.fitnesapp.demo.models.User;
import lombok.Data;
@Data
public class UserResponseDto {
    private String id;
    private String email;
    private String role;
    private String name;
    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.name = user.getName();
    }

}
