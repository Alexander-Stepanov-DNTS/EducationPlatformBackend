package ru.stepanov.EducationPlatform.services;

import ru.stepanov.EducationPlatform.DTO.LessonDto;

import java.util.List;

public interface LessonService {
    LessonDto getLessonById(Long id);
    List<LessonDto> getAllLessons();
    LessonDto createLesson(LessonDto lessonDto);
    LessonDto updateLesson(Long id, LessonDto lessonDto);
    void deleteLesson(Long id);
}

