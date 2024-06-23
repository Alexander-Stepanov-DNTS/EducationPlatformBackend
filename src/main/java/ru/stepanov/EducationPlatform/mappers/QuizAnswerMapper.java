package ru.stepanov.EducationPlatform.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.stepanov.EducationPlatform.DTO.QuizAnswerDto;
import ru.stepanov.EducationPlatform.models.QuizAnswer;

@Mapper
public interface QuizAnswerMapper {
    QuizAnswerMapper INSTANCE = Mappers.getMapper(QuizAnswerMapper.class);

    QuizAnswerDto toDto(QuizAnswer quizAnswer);
    QuizAnswer toEntity(QuizAnswerDto quizAnswerDto);
}
