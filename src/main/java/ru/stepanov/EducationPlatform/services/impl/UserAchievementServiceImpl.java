package ru.stepanov.EducationPlatform.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.UserAchievementDto;
import ru.stepanov.EducationPlatform.mappers.UserAchievementMapper;
import ru.stepanov.EducationPlatform.models.Achievement;
import ru.stepanov.EducationPlatform.models.User;
import ru.stepanov.EducationPlatform.models.UserAchievement;
import ru.stepanov.EducationPlatform.models.EmbeddedId.UserAchievementId;
import ru.stepanov.EducationPlatform.repositories.AchievementRepository;
import ru.stepanov.EducationPlatform.repositories.UserAchievementRepository;
import ru.stepanov.EducationPlatform.repositories.UserRepository;
import ru.stepanov.EducationPlatform.services.UserAchievementService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserAchievementServiceImpl implements UserAchievementService {

    private final UserAchievementRepository userAchievementRepository;
    private final UserRepository userRepository;
    private final AchievementRepository achievementRepository;

    @Autowired
    public UserAchievementServiceImpl(UserAchievementRepository userAchievementRepository, UserRepository userRepository, AchievementRepository achievementRepository) {
        this.userAchievementRepository = userAchievementRepository;
        this.userRepository = userRepository;
        this.achievementRepository = achievementRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserAchievementDto getUserAchievementById(Long userId, Long achievementId) {
        UserAchievementId id = new UserAchievementId(userId, achievementId);
        Optional<UserAchievement> userAchievement = userAchievementRepository.findById(id);
        return userAchievement.map(UserAchievementMapper.INSTANCE::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserAchievementDto> getAllUserAchievements() {
        return userAchievementRepository.findAll().stream()
                .map(UserAchievementMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserAchievementDto createUserAchievement(UserAchievementDto userAchievementDto) {
        User user = userRepository.findById(userAchievementDto.getStudent().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Achievement achievement = achievementRepository.findById(userAchievementDto.getAchievement().getId())
                .orElseThrow(() -> new RuntimeException("Achievement not found"));

        UserAchievement userAchievement = new UserAchievement();
        userAchievement.setStudent(user);
        userAchievement.setAchievement(achievement);
        userAchievement.setDateAchieved(userAchievementDto.getDateAchieved());
        userAchievement.setId(new UserAchievementId(user.getId(), achievement.getId()));

        userAchievement = userAchievementRepository.save(userAchievement);
        return UserAchievementMapper.INSTANCE.toDto(userAchievement);
    }

    @Override
    @Transactional
    public UserAchievementDto updateUserAchievement(Long userId, Long achievementId, UserAchievementDto userAchievementDto) {
        UserAchievementId id = new UserAchievementId(userId, achievementId);
        Optional<UserAchievement> existingUserAchievement = userAchievementRepository.findById(id);
        if (existingUserAchievement.isPresent()) {
            UserAchievement userAchievement = existingUserAchievement.get();
            userAchievement.setDateAchieved(userAchievementDto.getDateAchieved());
            userAchievement.setStudent(userRepository.findById(userAchievementDto.getStudent().getId())
                    .orElseThrow(() -> new RuntimeException("User not found")));
            userAchievement.setAchievement(achievementRepository.findById(userAchievementDto.getAchievement().getId())
                    .orElseThrow(() -> new RuntimeException("Achievement not found")));
            userAchievement = userAchievementRepository.save(userAchievement);
            return UserAchievementMapper.INSTANCE.toDto(userAchievement);
        } else {
            return null; // или выбросить исключение
        }
    }

    @Override
    @Transactional
    public void deleteUserAchievement(Long userId, Long achievementId) {
        UserAchievementId id = new UserAchievementId(userId, achievementId);
        userAchievementRepository.deleteById(id);
    }

    @Override
    public List<UserAchievementDto> getUserAchievementByUserId(Long userId) {
        return userAchievementRepository.findByStudentId(userId).stream()
                .map(UserAchievementMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }
}