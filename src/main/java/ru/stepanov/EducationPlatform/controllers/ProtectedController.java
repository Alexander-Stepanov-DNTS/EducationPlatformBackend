package ru.stepanov.EducationPlatform.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/protected-endpoint")
public class ProtectedController {

    @GetMapping
    public ResponseEntity<String> getProtectedResource() {
        return ResponseEntity.ok("This is a protected resource");
    }
}