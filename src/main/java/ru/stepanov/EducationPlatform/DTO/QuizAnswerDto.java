package ru.stepanov.EducationPlatform.DTO;

import lombok.Data;

@Data
public class QuizAnswerDto {
    private Long id;
    private QuizQuestionDto question;
    private String answerText;
    private Boolean isCorrect;
}