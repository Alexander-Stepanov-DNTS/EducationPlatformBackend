package ru.stepanov.EducationPlatform.services;

import ru.stepanov.EducationPlatform.DTO.LessonDto;
import ru.stepanov.EducationPlatform.DTO.QuizDto;
import ru.stepanov.EducationPlatform.DTO.QuizQuestionDto;

import java.util.List;

public interface QuizService {
    QuizDto getQuizById(Long id);
    List<QuizDto> getAllQuizzes();
    List<QuizDto> getQuizzesFromCourse(Long id);
    QuizDto createQuiz(QuizDto quizDto);
    QuizDto updateQuiz(Long id, QuizDto quizDto);
    void deleteQuiz(Long id);
    public List<QuizQuestionDto> getQuizQuestions(Long quizId);
}
