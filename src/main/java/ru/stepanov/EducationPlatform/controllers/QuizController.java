package ru.stepanov.EducationPlatform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stepanov.EducationPlatform.DTO.LessonDto;
import ru.stepanov.EducationPlatform.DTO.QuizDto;
import ru.stepanov.EducationPlatform.DTO.QuizQuestionDto;
import ru.stepanov.EducationPlatform.services.QuizService;

import java.util.List;

@RestController
@RequestMapping("/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/{id}")
    public ResponseEntity<QuizDto> getQuizById(@PathVariable Long id) {
        QuizDto quiz = quizService.getQuizById(id);
        return ResponseEntity.ok(quiz);
    }

    @GetMapping
    public ResponseEntity<List<QuizDto>> getAllQuizzes() {
        List<QuizDto> quizzes = quizService.getAllQuizzes();
        return ResponseEntity.ok(quizzes);
    }

    @PostMapping
    public ResponseEntity<QuizDto> createQuiz(@RequestBody QuizDto quizDto) {
        QuizDto createdQuiz = quizService.createQuiz(quizDto);
        return ResponseEntity.ok(createdQuiz);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizDto> updateQuiz(@PathVariable Long id, @RequestBody QuizDto quizDto) {
        QuizDto updatedQuiz = quizService.updateQuiz(id, quizDto);
        return ResponseEntity.ok(updatedQuiz);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<List<QuizDto>> getLessonsFromCourse(@PathVariable Long id){
        List<QuizDto> quizzes = quizService.getQuizzesFromCourse(id);
        return ResponseEntity.ok(quizzes);
    }

    @GetMapping("/{quizId}/questions")
    public ResponseEntity<List<QuizQuestionDto>> getQuizQuestions(@PathVariable Long quizId) {
        List<QuizQuestionDto> questions = quizService.getQuizQuestions(quizId);
        return ResponseEntity.ok(questions);
    }

//    @PostMapping("/{quizId}/submit")
//    public ResponseEntity<Void> submitQuiz(@PathVariable Long quizId, @RequestBody QuizSubmissionDto submission) {
//        quizService.submitQuiz(quizId, submission);
//        return ResponseEntity.ok().build();
//    }
}