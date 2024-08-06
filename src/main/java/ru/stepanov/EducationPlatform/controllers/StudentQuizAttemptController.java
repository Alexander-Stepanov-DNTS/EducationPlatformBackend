package ru.stepanov.EducationPlatform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.stepanov.EducationPlatform.DTO.StudentQuizAttemptDto;
import ru.stepanov.EducationPlatform.services.StudentQuizAttemptService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/student-quiz-attempts")
public class StudentQuizAttemptController {

    private final StudentQuizAttemptService studentQuizAttemptService;

    @Autowired
    public StudentQuizAttemptController(StudentQuizAttemptService studentQuizAttemptService) {
        this.studentQuizAttemptService = studentQuizAttemptService;
    }

    @GetMapping("/{studentId}/{quizId}/{attemptDatetime}")
    public ResponseEntity<StudentQuizAttemptDto> getStudentQuizAttemptById(@PathVariable Long studentId, @PathVariable Long quizId, @PathVariable LocalDateTime attemptDatetime) {
        StudentQuizAttemptDto studentQuizAttempt = studentQuizAttemptService.getStudentQuizAttemptById(studentId, quizId, attemptDatetime);
        return ResponseEntity.ok(studentQuizAttempt);
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkStudentQuizAttemptExists(@RequestParam Long studentId, @RequestParam Long quizId) {
        boolean exists = studentQuizAttemptService.studentQuizAttemptExists(studentId, quizId);
        return ResponseEntity.ok(exists);
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