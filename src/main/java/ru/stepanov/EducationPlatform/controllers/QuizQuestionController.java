package ru.stepanov.EducationPlatform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stepanov.EducationPlatform.DTO.QuizQuestionDto;
import ru.stepanov.EducationPlatform.services.QuizQuestionService;

import java.util.List;

@RestController
@RequestMapping("/quiz-questions")
public class QuizQuestionController {

    @Autowired
    private QuizQuestionService quizQuestionService;

    @GetMapping("/{id}")
    public ResponseEntity<QuizQuestionDto> getQuizQuestionById(@PathVariable Long id) {
        QuizQuestionDto quizQuestion = quizQuestionService.getQuizQuestionById(id);
        return ResponseEntity.ok(quizQuestion);
    }

    @GetMapping
    public ResponseEntity<List<QuizQuestionDto>> getAllQuizQuestions() {
        List<QuizQuestionDto> quizQuestions = quizQuestionService.getAllQuizQuestions();
        return ResponseEntity.ok(quizQuestions);
    }

    @PostMapping
    public ResponseEntity<QuizQuestionDto> createQuizQuestion(@RequestBody QuizQuestionDto quizQuestionDto) {
        QuizQuestionDto createdQuizQuestion = quizQuestionService.createQuizQuestion(quizQuestionDto);
        return ResponseEntity.ok(createdQuizQuestion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizQuestionDto> updateQuizQuestion(@PathVariable Long id, @RequestBody QuizQuestionDto quizQuestionDto) {
        QuizQuestionDto updatedQuizQuestion = quizQuestionService.updateQuizQuestion(id, quizQuestionDto);
        return ResponseEntity.ok(updatedQuizQuestion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuizQuestion(@PathVariable Long id) {
        quizQuestionService.deleteQuizQuestion(id);
        return ResponseEntity.noContent().build();
    }
}