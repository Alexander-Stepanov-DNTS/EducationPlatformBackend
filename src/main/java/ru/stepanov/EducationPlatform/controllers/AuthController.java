package ru.stepanov.EducationPlatform.controllers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import ru.stepanov.EducationPlatform.security.JwtTokenUtil;
import ru.stepanov.EducationPlatform.security.MyUserDetails;
import ru.stepanov.EducationPlatform.security.MyUserDetailsService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
            final MyUserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
            final String token = jwtTokenUtil.generateToken(userDetails);

            // Создание и настройка cookie
            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(true); // HTTP-Only
            cookie.setSecure(true); // Установите true для HTTPS, для HTTP установите false
            cookie.setPath("/"); // Доступен для всех путей
            cookie.setMaxAge(3600); // Время жизни cookie в секундах
//
//            // Добавление cookie в ответ
            response.addCookie(cookie);

            return ResponseEntity.ok(new AuthResponse("All correct!"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(new AuthResponse("Login failed"));
        }
    }
}

@Setter
@Getter
class AuthRequest {
    private String email;
    private String password;

    public AuthRequest(){}

    public AuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

}

@Setter
@Getter
class AuthResponse {
    private String message;
    public AuthResponse() {}

    public AuthResponse(String message) {
        this.message = message;
    }

}
