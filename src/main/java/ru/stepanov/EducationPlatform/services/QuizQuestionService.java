package ru.stepanov.EducationPlatform.services;

import ru.stepanov.EducationPlatform.DTO.QuizQuestionDto;

import java.util.List;

public interface QuizQuestionService {
    QuizQuestionDto getQuizQuestionById(Long id);
    List<QuizQuestionDto> getAllQuizQuestions();
    QuizQuestionDto createQuizQuestion(QuizQuestionDto quizQuestionDto);
    QuizQuestionDto updateQuizQuestion(Long id, QuizQuestionDto quizQuestionDto);
    void deleteQuizQuestion(Long id);
}
