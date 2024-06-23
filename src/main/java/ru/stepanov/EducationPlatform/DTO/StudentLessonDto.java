package ru.stepanov.EducationPlatform.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentLessonDto {
    private UserDto student;
    private LessonDto lesson;
    private LocalDateTime completedDatetime;
}

