package ru.stepanov.EducationPlatform.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class QuizDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdDate;
    private Boolean isActive;
    private Date dueDate;
    private Date reminderDate;
    private Integer courseOrder;
    private CourseDto course;
}