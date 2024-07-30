package ru.stepanov.EducationPlatform.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.stepanov.EducationPlatform.DTO.AchievementDto;
import ru.stepanov.EducationPlatform.models.Achievement;

@Mapper
public interface AchievementMapper {
    AchievementMapper INSTANCE = Mappers.getMapper(AchievementMapper.class);

    AchievementDto toDto(Achievement achievement);
    Achievement toEntity(AchievementDto achievementDto);
}

