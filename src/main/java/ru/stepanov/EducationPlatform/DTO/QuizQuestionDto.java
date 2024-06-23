package ru.stepanov.EducationPlatform.DTO;

import lombok.Data;

@Data
public class QuizQuestionDto {
    private Long id;
    private QuizDto quiz;
    private String questionTitle;
    private Boolean manyAnswers;
}
