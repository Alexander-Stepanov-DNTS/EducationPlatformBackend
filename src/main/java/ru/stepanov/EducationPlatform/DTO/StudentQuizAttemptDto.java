package ru.stepanov.EducationPlatform.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentQuizAttemptDto {
    private UserDto student;
    private QuizDto quiz;
    private LocalDateTime attemptDatetime;
    private Integer scoreAchieved;
}