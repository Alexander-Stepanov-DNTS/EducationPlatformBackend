package ru.stepanov.EducationPlatform.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EnrolmentDto {
    private CourseDto course;
    private UserDto student;
    private LocalDateTime enrolmentDatetime;
    private LocalDateTime completedDatetime;
    private Boolean isAuthor;
    private Long progress;
}