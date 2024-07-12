package ru.stepanov.EducationPlatform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stepanov.EducationPlatform.DTO.QuizAnswerDto;
import ru.stepanov.EducationPlatform.services.QuizAnswerService;

import java.util.List;

@RestController
@RequestMapping("/quiz-answers")
public class QuizAnswerController {

    @Autowired
    private QuizAnswerService quizAnswerService;

    @GetMapping("/{id}")
    public ResponseEntity<QuizAnswerDto> getQuizAnswerById(@PathVariable Long id) {
        QuizAnswerDto quizAnswer = quizAnswerService.getQuizAnswerById(id);
        return ResponseEntity.ok(quizAnswer);
    }

    @GetMapping
    public ResponseEntity<List<QuizAnswerDto>> getAllQuizAnswers() {
        List<QuizAnswerDto> quizAnswers = quizAnswerService.getAllQuizAnswers();
        return ResponseEntity.ok(quizAnswers);
    }

    @PostMapping
    public ResponseEntity<QuizAnswerDto> createQuizAnswer(@RequestBody QuizAnswerDto quizAnswerDto) {
        QuizAnswerDto createdQuizAnswer = quizAnswerService.createQuizAnswer(quizAnswerDto);
        return ResponseEntity.ok(createdQuizAnswer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizAnswerDto> updateQuizAnswer(@PathVariable Long id, @RequestBody QuizAnswerDto quizAnswerDto) {
        QuizAnswerDto updatedQuizAnswer = quizAnswerService.updateQuizAnswer(id, quizAnswerDto);
        return ResponseEntity.ok(updatedQuizAnswer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuizAnswer(@PathVariable Long id) {
        quizAnswerService.deleteQuizAnswer(id);
        return ResponseEntity.noContent().build();
    }
}