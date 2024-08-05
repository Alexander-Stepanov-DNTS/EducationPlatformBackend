package ru.stepanov.EducationPlatform.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.stepanov.EducationPlatform.security.JwtTokenUtil;
import ru.stepanov.EducationPlatform.security.MyUserDetails;
import ru.stepanov.EducationPlatform.security.MyUserDetailsService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final MyUserDetailsService userDetailsService;

    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, MyUserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        System.out.println(authRequest.getEmail_address() + " " + authRequest.getLogin() + " " + authRequest.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail_address(), authRequest.getPassword())
            );
            final MyUserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail_address());
            final String token = jwtTokenUtil.generateToken(userDetails);

            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(true); // HTTP-Only
            cookie.setSecure(true); // true для HTTPS, для HTTP false
            cookie.setPath("/"); // Доступен для всех путей
            cookie.setMaxAge(360000); // Время жизни cookie в секундах

            response.addCookie(cookie);

            return ResponseEntity.ok(new AuthResponse("All correct!"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(new AuthResponse("Login failed"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // Установить время жизни cookies на 0, чтобы удалить его

        response.addCookie(cookie);

        return ResponseEntity.ok(new AuthResponse("Logout successful!"));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(HttpServletRequest request) {
        Integer userID = (Integer) request.getAttribute("id");

        if (userID == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Пользователь не авторизован");
        }

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("id", userID.toString());
        userInfo.put("login", (String) request.getAttribute("login"));
        userInfo.put("role", (String) request.getAttribute("role"));

        return ResponseEntity.ok(userInfo);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody AuthRequest authRequest) {
        if (userDetailsService.existsByEmail(authRequest.getEmail_address())) {
            return ResponseEntity.badRequest().body("Email уже используется");
        }

        userDetailsService.saveUser(authRequest.getLogin(), authRequest.getPassword(), authRequest.getEmail_address());

        return ResponseEntity.ok("Регистрация прошла успешно");
    }
}

@Setter
@Getter
class AuthRequest {
    private String id;
    private String login;
    private String email_address;
    private String password;

    public AuthRequest(){}

    public AuthRequest(String email_address, String password, String login) {
        this.email_address = email_address;
        this.password = password;
        this.login = login;
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
