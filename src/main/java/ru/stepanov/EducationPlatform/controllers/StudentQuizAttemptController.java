package ru.stepanov.EducationPlatform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stepanov.EducationPlatform.DTO.StudentQuizAttemptDto;
import ru.stepanov.EducationPlatform.services.StudentQuizAttemptService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/student-quiz-attempts")
public class StudentQuizAttemptController {

    @Autowired
    private StudentQuizAttemptService studentQuizAttemptService;

    @GetMapping("/{studentId}/{quizId}/{attemptDatetime}")
    public ResponseEntity<StudentQuizAttemptDto> getStudentQuizAttemptById(@PathVariable Long studentId, @PathVariable Long quizId, @PathVariable LocalDateTime attemptDatetime) {
        StudentQuizAttemptDto studentQuizAttempt = studentQuizAttemptService.getStudentQuizAttemptById(studentId, quizId, attemptDatetime);
        return ResponseEntity.ok(studentQuizAttempt);
    }

    @GetMapping
    public ResponseEntity<List<StudentQuizAttemptDto>> getAllStudentQuizAttempts() {
        List<StudentQuizAttemptDto> studentQuizAttempts = studentQuizAttemptService.getAllStudentQuizAttempts();
        return ResponseEntity.ok(studentQuizAttempts);
    }

    @PostMapping
    public ResponseEntity<StudentQuizAttemptDto> createStudentQuizAttempt(@RequestBody StudentQuizAttemptDto studentQuizAttemptDto) {
        StudentQuizAttemptDto createdStudentQuizAttempt = studentQuizAttemptService.createStudentQuizAttempt(studentQuizAttemptDto);
        return ResponseEntity.ok(createdStudentQuizAttempt);
    }

    @PutMapping("/{studentId}/{quizId}/{attemptDatetime}")
    public ResponseEntity<StudentQuizAttemptDto> updateStudentQuizAttempt(@PathVariable Long studentId, @PathVariable Long quizId, @PathVariable LocalDateTime attemptDatetime, @RequestBody StudentQuizAttemptDto studentQuizAttemptDto) {
        StudentQuizAttemptDto updatedStudentQuizAttempt = studentQuizAttemptService.updateStudentQuizAttempt(studentId, quizId, attemptDatetime, studentQuizAttemptDto);
        return ResponseEntity.ok(updatedStudentQuizAttempt);
    }

    @DeleteMapping("/{studentId}/{quizId}/{attemptDatetime}")
    public ResponseEntity<Void> deleteStudentQuizAttempt(@PathVariable Long studentId, @PathVariable Long quizId, @PathVariable LocalDateTime attemptDatetime) {
        studentQuizAttemptService.deleteStudentQuizAttempt(studentId, quizId, attemptDatetime);
        return ResponseEntity.noContent().build();
    }
}
