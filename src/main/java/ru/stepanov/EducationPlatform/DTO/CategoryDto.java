package ru.stepanov.EducationPlatform.DTO;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {
    private Long id;
    private String name;
    private String description;
    private DirectionDto direction;
}

