package ru.stepanov.EducationPlatform.DTO;
import lombok.Data;

@Data
public class LoginResponseDTO {
    private String message;

    LoginResponseDTO(){}

    public LoginResponseDTO(String message) {
        this.message = message;
    }
}
