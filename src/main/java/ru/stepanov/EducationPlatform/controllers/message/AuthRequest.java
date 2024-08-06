package ru.stepanov.EducationPlatform.controllers.message;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AuthRequest {
    private String id;
    private String login;
    private String email_address;
    private String password;

    public AuthRequest(String email_address, String password, String login) {
        this.email_address = email_address;
        this.password = password;
        this.login = login;
    }

}
