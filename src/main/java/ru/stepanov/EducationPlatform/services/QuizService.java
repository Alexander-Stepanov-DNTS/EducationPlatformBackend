package ru.stepanov.EducationPlatform.services;

import ru.stepanov.EducationPlatform.DTO.QuizDto;

import java.util.List;

public interface QuizService {
    QuizDto getQuizById(Long id);
    List<QuizDto> getAllQuizzes();
    QuizDto createQuiz(QuizDto quizDto);
    QuizDto updateQuiz(Long id, QuizDto quizDto);
    void deleteQuiz(Long id);
}
