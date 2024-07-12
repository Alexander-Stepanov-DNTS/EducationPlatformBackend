package ru.stepanov.EducationPlatform.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {
    private Long id;
    private String emailAddress;
    private String password;
    private LocalDate signupDate;
    private RoleDto role;
    private InstitutionDto institution;
}
