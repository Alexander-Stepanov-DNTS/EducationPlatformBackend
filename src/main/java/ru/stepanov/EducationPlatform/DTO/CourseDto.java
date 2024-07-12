package ru.stepanov.EducationPlatform.DTO;

import lombok.Data;

@Data
public class CourseDto {
    private Long id;
    private String name;
    private String description;
    private Long price;
    private Boolean isProgressLimited;
    private CategoryDto category;
}

