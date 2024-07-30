package ru.stepanov.EducationPlatform.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.stepanov.EducationPlatform.DTO.UserAchievementDto;
import ru.stepanov.EducationPlatform.models.UserAchievement;

@Mapper
public interface UserAchievementMapper {
    UserAchievementMapper INSTANCE = Mappers.getMapper(UserAchievementMapper.class);

    UserAchievementDto toDto(UserAchievement userAchievement);

    UserAchievement toEntity(UserAchievementDto userAchievementDto);
}

