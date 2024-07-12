package ru.stepanov.EducationPlatform.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.stepanov.EducationPlatform.DTO.LessonDto;
import ru.stepanov.EducationPlatform.models.Lesson;

@Mapper
public interface LessonMapper {
    LessonMapper INSTANCE = Mappers.getMapper(LessonMapper.class);

    LessonDto toDto(Lesson lesson);
    Lesson toEntity(LessonDto lessonDto);
}

