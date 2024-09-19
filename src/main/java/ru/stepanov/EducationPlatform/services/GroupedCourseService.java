package ru.stepanov.EducationPlatform.services;

import java.util.List;
import java.util.Map;

import ru.stepanov.EducationPlatform.DTO.CourseDto;

public interface GroupedCourseService {
    Map<String, List<Map<String, Object>>> getCoursesGroupedByCategory();

    Map<String, List<CourseDto>> getCoursesGroupedByRating();
}
