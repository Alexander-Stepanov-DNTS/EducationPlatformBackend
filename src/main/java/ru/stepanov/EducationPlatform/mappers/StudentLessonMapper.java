package ru.stepanov.EducationPlatform.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.stepanov.EducationPlatform.DTO.StudentLessonDto;
import ru.stepanov.EducationPlatform.models.StudentLesson;

@Mapper
public interface StudentLessonMapper {
    StudentLessonMapper INSTANCE = Mappers.getMapper(StudentLessonMapper.class);

    StudentLessonDto toDto(StudentLesson studentLesson);
    StudentLesson toEntity(StudentLessonDto studentLessonDto);
}
