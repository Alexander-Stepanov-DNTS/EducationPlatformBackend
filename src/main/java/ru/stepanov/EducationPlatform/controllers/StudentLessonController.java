package ru.stepanov.EducationPlatform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stepanov.EducationPlatform.DTO.StudentLessonDto;
import ru.stepanov.EducationPlatform.services.StudentLessonService;

import java.util.List;

@RestController
@RequestMapping("/student-lessons")
public class StudentLessonController {

    @Autowired
    private StudentLessonService studentLessonService;

    @GetMapping("/{studentId}/{lessonId}")
    public ResponseEntity<StudentLessonDto> getStudentLessonById(@PathVariable Long studentId, @PathVariable Long lessonId) {
        StudentLessonDto studentLesson = studentLessonService.getStudentLessonById(studentId, lessonId);
        return ResponseEntity.ok(studentLesson);
    }

    @GetMapping
    public ResponseEntity<List<StudentLessonDto>> getAllStudentLessons() {
        List<StudentLessonDto> studentLessons = studentLessonService.getAllStudentLessons();
        return ResponseEntity.ok(studentLessons);
    }

    @PostMapping
    public ResponseEntity<StudentLessonDto> createStudentLesson(@RequestBody StudentLessonDto studentLessonDto) {
        StudentLessonDto createdStudentLesson = studentLessonService.createStudentLesson(studentLessonDto);
        return ResponseEntity.ok(createdStudentLesson);
    }

    @PutMapping("/{studentId}/{lessonId}")
    public ResponseEntity<StudentLessonDto> updateStudentLesson(@PathVariable Long studentId, @PathVariable Long lessonId, @RequestBody StudentLessonDto studentLessonDto) {
        StudentLessonDto updatedStudentLesson = studentLessonService.updateStudentLesson(studentId, lessonId, studentLessonDto);
        return ResponseEntity.ok(updatedStudentLesson);
    }

    @DeleteMapping("/{studentId}/{lessonId}")
    public ResponseEntity<Void> deleteStudentLesson(@PathVariable Long studentId, @PathVariable Long lessonId) {
        studentLessonService.deleteStudentLesson(studentId, lessonId);
        return ResponseEntity.noContent().build();
    }
}