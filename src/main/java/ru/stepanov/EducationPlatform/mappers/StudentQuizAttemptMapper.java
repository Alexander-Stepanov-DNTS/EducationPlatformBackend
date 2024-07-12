package ru.stepanov.EducationPlatform.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.stepanov.EducationPlatform.DTO.StudentQuizAttemptDto;
import ru.stepanov.EducationPlatform.models.StudentQuizAttempt;

@Mapper
public interface StudentQuizAttemptMapper {
    StudentQuizAttemptMapper INSTANCE = Mappers.getMapper(StudentQuizAttemptMapper.class);

    StudentQuizAttemptDto toDto(StudentQuizAttempt studentQuizAttempt);
    StudentQuizAttempt toEntity(StudentQuizAttemptDto studentQuizAttemptDto);
}
