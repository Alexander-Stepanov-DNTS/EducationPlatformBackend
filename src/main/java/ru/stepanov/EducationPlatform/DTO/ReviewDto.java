package ru.stepanov.EducationPlatform.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewDto {
    private Long id;
    private UserDto user;
    private CourseDto course;
    private LocalDateTime createdDate;
    private Integer score;
    private String reviewText;
}