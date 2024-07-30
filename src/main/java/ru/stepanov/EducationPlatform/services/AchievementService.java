package ru.stepanov.EducationPlatform.services;

import ru.stepanov.EducationPlatform.DTO.AchievementDto;
import java.util.List;

public interface AchievementService {
    AchievementDto getAchievementById(Long id);
    List<AchievementDto> getAllAchievements();
    AchievementDto createAchievement(AchievementDto achievementDto);
    AchievementDto updateAchievement(Long id, AchievementDto achievementDto);
    void deleteAchievement(Long id);
}

