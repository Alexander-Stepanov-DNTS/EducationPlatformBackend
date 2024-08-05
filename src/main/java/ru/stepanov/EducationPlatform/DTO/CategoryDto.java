package ru.stepanov.EducationPlatform.DTO;

import lombok.Data;

@Data
public class CategoryDto {
    private Long id;
    private String name;
    private String description;
    private DirectionDto direction;
}