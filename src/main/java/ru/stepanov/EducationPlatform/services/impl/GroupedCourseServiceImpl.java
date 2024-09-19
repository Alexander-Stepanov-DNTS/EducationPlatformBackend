package ru.stepanov.EducationPlatform.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.stepanov.EducationPlatform.DTO.CourseDto;
import ru.stepanov.EducationPlatform.repositories.CourseRepository;
import ru.stepanov.EducationPlatform.services.GroupedCourseService;

@Service
public class GroupedCourseServiceImpl implements GroupedCourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public GroupedCourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Map<String, List<Map<String, Object>>> getCoursesGroupedByCategory() {
        List<Object[]> rawData = courseRepository.findCoursesGroupedByCategory();

        Map<String, List<Map<String, Object>>> groupedCourses = new HashMap<>();

        for (Object[] row : rawData) {
            String categoryName = (String) row[0];
            Long courseId = (Long) row[1];
            String courseName = (String) row[2];
            String courseDescription = (String) row[3];
            Float courseRating = (Float) row[4];
            Boolean isProgressLimited = (Boolean) row[5];
            String pictureUrl = (String) row[6];

            Map<String, Object> courseData = new HashMap<>();
            courseData.put("id", courseId);
            courseData.put("name", courseName);
            courseData.put("description", courseDescription);
            courseData.put("rating", courseRating);
            courseData.put("isProgressLimited", isProgressLimited);
            courseData.put("picture_url", pictureUrl);

            groupedCourses
                    .computeIfAbsent(categoryName, k -> new ArrayList<>())
                    .add(courseData);
        }

        return groupedCourses;
    }

    public Map<String, List<CourseDto>> getCoursesGroupedByRating() {
        List<Object[]> rawData = courseRepository.findCoursesGroupedByRating();

        Map<String, List<CourseDto>> groupedCourses = new HashMap<>();

        for (Object[] row : rawData) {
            String ratingGroup = (String) row[0];
            Long courseId = (Long) row[1];
            String courseName = (String) row[2];
            String courseDescription = (String) row[3];
            Boolean isProgressLimited = (Boolean) row[4];
            String pictureUrl = (String) row[5];
            Float rating = (Float) row[6];

            CourseDto courseDto = new CourseDto();
            courseDto.setId(courseId);
            courseDto.setName(courseName);
            courseDto.setDescription(courseDescription);
            courseDto.setIsProgressLimited(isProgressLimited);
            courseDto.setPicture_url(pictureUrl);
            courseDto.setRating(rating);

            groupedCourses
                    .computeIfAbsent(ratingGroup, k -> new ArrayList<>())
                    .add(courseDto);
        }

        return groupedCourses;
    }
}
