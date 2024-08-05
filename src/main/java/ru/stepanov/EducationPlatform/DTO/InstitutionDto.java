package ru.stepanov.EducationPlatform.DTO;

import lombok.Data;

@Data
public class InstitutionDto {
    private Long id;
    private String name;
    private String type;
}