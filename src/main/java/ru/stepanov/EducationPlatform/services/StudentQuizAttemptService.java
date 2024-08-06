package ru.stepanov.EducationPlatform.services;

import ru.stepanov.EducationPlatform.DTO.StudentQuizAttemptDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StudentQuizAttemptService {
    StudentQuizAttemptDto getStudentQuizAttemptById(Long studentId, Long quizId, LocalDateTime attemptDatetime);
    boolean studentQuizAttemptExists(Long studentId, Long quizId);
    List<StudentQuizAttemptDto> getAllStudentQuizAttempts();
    StudentQuizAttemptDto createStudentQuizAttempt(StudentQuizAttemptDto studentQuizAttemptDto);
    StudentQuizAttemptDto updateStudentQuizAttempt(Long studentId, Long quizId, LocalDateTime attemptDatetime, StudentQuizAttemptDto studentQuizAttemptDto);
    void deleteStudentQuizAttempt(Long studentId, Long quizId, LocalDateTime attemptDatetime);
}
