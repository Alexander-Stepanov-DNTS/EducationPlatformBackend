package ru.stepanov.EducationPlatform.DTO;

import lombok.Data;

@Data
public class CourseMaterialDto {
    private Long id;
    private CourseDto course;
    private String materialTitle;
    private String materialUrl;
}