package ru.stepanov.EducationPlatform.security.userDetails;

import org.springframework.security.core.GrantedAuthority;
import ru.stepanov.EducationPlatform.models.User;

import java.util.Collection;

public class MyUserDetails implements CustomUserDetails {

    private final User user;

    public MyUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;//Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmailAddress();
    }

    public String getLogin() {
        return user.getLogin();
    }

    public Long getUserId() {
        return user.getId();
    }

    public String getUserRole() {
        return user.getRole().getName();
    }
}