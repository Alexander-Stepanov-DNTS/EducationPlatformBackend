package ru.stepanov.EducationPlatform.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserAchievementDto {
    private UserDto student;
    private AchievementDto achievement;
    private LocalDate dateAchieved;
}

