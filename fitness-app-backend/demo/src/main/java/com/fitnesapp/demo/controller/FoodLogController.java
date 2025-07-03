package com.fitnesapp.demo.controller;

import com.fitnesapp.demo.dto.FoodLogDto;
import com.fitnesapp.demo.services.FoodLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/food-logs")
public class FoodLogController {
    @Autowired
    private FoodLogService foodLogService;

    @PostMapping
    public FoodLogDto addFoodLog(@RequestBody FoodLogDto dto) {
        return foodLogService.save(dto);
    }
    @GetMapping("/user/{userId}/summary")
    public Map<String, Object> getUserFoodLogSummary(@PathVariable String userId) {
        List<FoodLogDto> logs = foodLogService.getAllByUserIdOrderByCreatedAtDesc(userId);
        double totalCalories = logs.stream().mapToDouble(FoodLogDto::getTotalCalories).sum();
        Map<String, Object> result = new HashMap<>();   
        result.put("logs", logs);
        result.put("totalCalories", totalCalories);
        return result;
    }
        @GetMapping("/user/{userId}")
        public List<FoodLogDto> getFoodLogsByUser(@PathVariable String userId) {
            return foodLogService.getAllByUserId(userId);
        }
} 