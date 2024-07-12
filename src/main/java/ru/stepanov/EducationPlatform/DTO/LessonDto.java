package ru.stepanov.EducationPlatform.DTO;

import lombok.Data;

@Data
public class LessonDto {
    private Long id;
    private String name;
    private Integer number;
    private String videoUrl;
    private String lessonDetails;
    private Integer courseOrder;
    private CourseDto course;
}
