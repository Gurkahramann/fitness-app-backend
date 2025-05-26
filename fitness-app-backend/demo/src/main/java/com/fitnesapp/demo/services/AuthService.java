package com.fitnesapp.demo.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitnesapp.demo.dto.UserInfoDto;
import com.fitnesapp.demo.models.User;
import com.fitnesapp.demo.repositories.UserRepository;
import com.fitnesapp.demo.security.JwtUtil;
@Service
public class AuthService {
       @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    public Optional<User> validateTokenAndGetUser(String token) {
        if (!jwtUtil.validateToken(token)) return Optional.empty();

        String userId = jwtUtil.extractUserId(token);
        return userRepository.findById(userId);
    }
    public UserInfoDto getUserInfo(String userId) {
        return userRepository.findById(userId)
            .map(user -> {
                // 👇 Burada tarih formatlıyoruz
                String formattedBirthDate = formatDate(user.getBirthDate());
                return new UserInfoDto(
                    user.getGender(),
                    user.getHeight(),
                    user.getWeight(),
                    user.getAge(),
                    user.getActivityLevel(),
                    user.getGoal(),
                    formattedBirthDate // 👈 artık string olarak gönderiyoruz
                );
            })
            .orElse(null);
    }
    private String formatDate(String dateStr) {
    try {
        // Orijinal string: "Sat May 28 03:00:00 TRT 1983"
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date parsedDate = inputFormat.parse(dateStr);
        return outputFormat.format(parsedDate);
    } catch (Exception e) {
        return dateStr; // Formatlanamazsa orijinal halini döndür
    }
}

}
