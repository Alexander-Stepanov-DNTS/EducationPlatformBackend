package ru.stepanov.EducationPlatform.DTO;

import lombok.Data;

@Data
public class CourseDto {
    private Long id;
    private String name;
    private String description;
    private Float rating;
    private Boolean isProgressLimited;
    private String picture_url;
    private CategoryDto category;
}