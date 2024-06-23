package ru.stepanov.EducationPlatform.DTO;

import lombok.Data;
import ru.stepanov.EducationPlatform.models.User;

import java.util.List;

@Data
public class RoleDto {
    private Long id;
    private String name;
}
