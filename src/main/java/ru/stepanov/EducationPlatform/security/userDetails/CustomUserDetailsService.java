package ru.stepanov.EducationPlatform.security.userDetails;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsService extends UserDetailsService {
    boolean existsByEmail(String emailAddress);
    MyUserDetails saveUser(String login, String password, String emailAddress);
}