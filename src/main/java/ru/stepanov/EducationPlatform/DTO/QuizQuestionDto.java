package ru.stepanov.EducationPlatform.DTO;

import lombok.Data;

import java.util.List;

@Data
public class QuizQuestionDto {
    private Long id;
    private QuizDto quiz;
    private String questionTitle;
    private Boolean manyAnswers;
    private List<QuizAnswerDto> options;
}