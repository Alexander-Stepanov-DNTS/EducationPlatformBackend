package ru.stepanov.EducationPlatform.services;

import ru.stepanov.EducationPlatform.DTO.CourseDto;

import java.util.List;

public interface CourseService {
    CourseDto getCourseById(Long id);
    List<CourseDto> getAllCourses();
    CourseDto createCourse(CourseDto courseDto);
    CourseDto updateCourse(Long id, CourseDto courseDto);
    void deleteCourse(Long id);
}

