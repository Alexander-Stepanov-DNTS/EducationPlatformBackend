package ru.stepanov.EducationPlatform.services;

import ru.stepanov.EducationPlatform.DTO.UserAchievementDto;
import java.util.List;

public interface UserAchievementService {
    UserAchievementDto getUserAchievementById(Long userId, Long achievementId);
    List<UserAchievementDto> getAllUserAchievements();
    UserAchievementDto createUserAchievement(UserAchievementDto userAchievementDto);
    UserAchievementDto updateUserAchievement(Long userId, Long achievementId, UserAchievementDto userAchievementDto);
    void deleteUserAchievement(Long userId, Long achievementId);
    List<UserAchievementDto> getUserAchievementByUserId(Long userId);
}

