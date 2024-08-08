package ru.stepanov.EducationPlatform.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class StudentQuizAttemptDto {
    private UserDto student;
    private QuizDto quiz;
    private LocalDateTime attemptDatetime;
    private Integer scoreAchieved;
    private Map<Long, Long> answers; // Добавляем это поле
}