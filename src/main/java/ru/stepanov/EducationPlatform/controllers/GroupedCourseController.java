package ru.stepanov.EducationPlatform.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.stepanov.EducationPlatform.DTO.CourseDto;
import ru.stepanov.EducationPlatform.services.GroupedCourseService;

@RestController
@RequestMapping("/grouped-courses")
public class GroupedCourseController {
    private final GroupedCourseService groupedCourseService;

    @Autowired
    public GroupedCourseController(GroupedCourseService groupedCourseService) {
        this.groupedCourseService = groupedCourseService;
    }

    @GetMapping("/group-by-category")
    public ResponseEntity<Map<String, List<Map<String, Object>>>> getCoursesByCategory() {
        Map<String, List<Map<String, Object>>> groupedCourses = groupedCourseService.getCoursesGroupedByCategory();

        return ResponseEntity.ok(groupedCourses);
    }

    @GetMapping("/group-by-rating")
    public ResponseEntity<?> getCoursesByRating() {
        Map<String, List<CourseDto>> groupedCourses = groupedCourseService.getCoursesGroupedByRating();

        return ResponseEntity.ok(groupedCourses);
    }
}
