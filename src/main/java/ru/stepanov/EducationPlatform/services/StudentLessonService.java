package ru.stepanov.EducationPlatform.services;

import ru.stepanov.EducationPlatform.DTO.StudentLessonDto;

import java.util.List;

public interface StudentLessonService {
    StudentLessonDto getStudentLessonById(Long studentId, Long lessonId);
    List<StudentLessonDto> getAllStudentLessons();
    StudentLessonDto createStudentLesson(StudentLessonDto studentLessonDto);
    StudentLessonDto updateStudentLesson(Long studentId, Long lessonId, StudentLessonDto studentLessonDto);
    void deleteStudentLesson(Long studentId, Long lessonId);
}

