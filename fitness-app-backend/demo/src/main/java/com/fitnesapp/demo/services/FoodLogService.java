package com.fitnesapp.demo.services;

import com.fitnesapp.demo.dto.FoodLogDto;
import com.fitnesapp.demo.models.FoodLog;
import com.fitnesapp.demo.repositories.FoodLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodLogService {
    @Autowired
    private FoodLogRepository foodLogRepository;

    public FoodLogDto save(FoodLogDto dto) {
        FoodLog foodLog = dtoToEntity(dto);
        FoodLog saved = foodLogRepository.save(foodLog);
        return entityToDto(saved);
    }

    public List<FoodLogDto> getAllByUserId(String userId) {
        return foodLogRepository.findAllByUserId(userId)
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public Optional<FoodLogDto> getById(Long id) {
        return foodLogRepository.findById(id).map(this::entityToDto);
    }
    public List<FoodLogDto> getAllByUserIdOrderByCreatedAtDesc(String userId) {
        return foodLogRepository.findAllByUserIdOrderByCreatedAtDesc(userId)
            .stream()
            .map(this::entityToDto)
            .collect(Collectors.toList());
    }
    
    public double getTotalCaloriesByUserId(String userId) {
        return foodLogRepository.findAllByUserId(userId)
            .stream()
            .mapToDouble(FoodLog::getTotalCalories)
            .sum();
    }
    private FoodLog dtoToEntity(FoodLogDto dto) {
        return new FoodLog(
                dto.getId(),
                dto.getUserId(),
                dto.getFoodName(),
                dto.getEstimatedGrams(),
                dto.getTotalCalories(),
                dto.getCaloriesPer100g(),
                dto.getProteinPer100g(),
                dto.getCarbsPer100g(),
                dto.getFatPer100g(),
                dto.getCreatedAt()
        );
    }

    private FoodLogDto entityToDto(FoodLog entity) {
        return new FoodLogDto(
                entity.getId(),
                entity.getUserId(),
                entity.getFoodName(),
                entity.getEstimatedGrams(),
                entity.getTotalCalories(),
                entity.getCaloriesPer100g(),
                entity.getProteinPer100g(),
                entity.getCarbsPer100g(),
                entity.getFatPer100g(),
                entity.getCreatedAt()
        );
    }
} 