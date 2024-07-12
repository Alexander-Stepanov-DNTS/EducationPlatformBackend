package ru.stepanov.EducationPlatform.services;

import ru.stepanov.EducationPlatform.DTO.QuizAnswerDto;

import java.util.List;

public interface QuizAnswerService {
    QuizAnswerDto getQuizAnswerById(Long id);
    List<QuizAnswerDto> getAllQuizAnswers();
    QuizAnswerDto createQuizAnswer(QuizAnswerDto quizAnswerDto);
    QuizAnswerDto updateQuizAnswer(Long id, QuizAnswerDto quizAnswerDto);
    void deleteQuizAnswer(Long id);
}

