package ru.stepanov.EducationPlatform.controllers.message;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AuthResponse {
    private String message;

    public AuthResponse(String message) {
        this.message = message;
    }
}
