package ru.stepanov.EducationPlatform.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.stepanov.EducationPlatform.DTO.QuizQuestionDto;
import ru.stepanov.EducationPlatform.models.QuizQuestion;

@Mapper
public interface QuizQuestionMapper {
    QuizQuestionMapper INSTANCE = Mappers.getMapper(QuizQuestionMapper.class);
    QuizQuestionDto toDto(QuizQuestion quizQuestion);
    QuizQuestion toEntity(QuizQuestionDto quizQuestionDto);
}