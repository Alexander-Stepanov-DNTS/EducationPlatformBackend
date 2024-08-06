package ru.stepanov.EducationPlatform.security.userDetails;
import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetails extends UserDetails {
    Long getUserId();
    String getUserRole();
    String getLogin();
}
