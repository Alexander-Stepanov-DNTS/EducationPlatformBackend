package ru.stepanov.EducationPlatform.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.AchievementDto;
import ru.stepanov.EducationPlatform.mappers.AchievementMapper;
import ru.stepanov.EducationPlatform.models.Achievement;
import ru.stepanov.EducationPlatform.repositories.AchievementRepository;
import ru.stepanov.EducationPlatform.services.AchievementService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository achievementRepository;

    @Autowired
    public AchievementServiceImpl(AchievementRepository achievementRepository) {
        this.achievementRepository = achievementRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public AchievementDto getAchievementById(Long id) {
        Optional<Achievement> achievement = achievementRepository.findById(id);
        return achievement.map(AchievementMapper.INSTANCE::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AchievementDto> getAllAchievements() {
        return achievementRepository.findAll().stream()
                .map(AchievementMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AchievementDto createAchievement(AchievementDto achievementDto) {
        Achievement achievement = AchievementMapper.INSTANCE.toEntity(achievementDto);
        achievement = achievementRepository.save(achievement);
        return AchievementMapper.INSTANCE.toDto(achievement);
    }

    @Override
    @Transactional
    public AchievementDto updateAchievement(Long id, AchievementDto achievementDto) {
        Optional<Achievement> existingAchievement = achievementRepository.findById(id);
        if (existingAchievement.isPresent()) {
            Achievement achievement = existingAchievement.get();
            achievement.setTitle(achievementDto.getTitle());
            achievement.setDescription(achievementDto.getDescription());
            achievement = achievementRepository.save(achievement);
            return AchievementMapper.INSTANCE.toDto(achievement);
        } else {
            return null; // или выбросить исключение
        }
    }

    @Override
    @Transactional
    public void deleteAchievement(Long id) {
        achievementRepository.deleteById(id);
    }
}