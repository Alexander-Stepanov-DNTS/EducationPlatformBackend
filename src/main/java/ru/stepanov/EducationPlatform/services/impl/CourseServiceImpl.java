package ru.stepanov.EducationPlatform.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.CourseDto;
import ru.stepanov.EducationPlatform.mappers.CategoryMapper;
import ru.stepanov.EducationPlatform.mappers.CourseMapper;
import ru.stepanov.EducationPlatform.models.Course;
import ru.stepanov.EducationPlatform.repositories.CourseRepository;
import ru.stepanov.EducationPlatform.services.CourseService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDto getCourseById(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        return course.map(CourseMapper.INSTANCE::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseDto> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(CourseMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CourseDto createCourse(CourseDto courseDto) {
        Course course = CourseMapper.INSTANCE.toEntity(courseDto);
        course = courseRepository.save(course);
        return CourseMapper.INSTANCE.toDto(course);
    }

    @Override
    @Transactional
    public CourseDto updateCourse(Long id, CourseDto courseDto) {
        Optional<Course> existingCourse = courseRepository.findById(id);
        if (existingCourse.isPresent()) {
            Course course = existingCourse.get();
            course.setName(courseDto.getName());
            course.setDescription(courseDto.getDescription());
            course.setRating(courseDto.getRating());
            course.setIsProgressLimited(courseDto.getIsProgressLimited());
            course.setPicture_url(courseDto.getPicture_url());
            course.setCategory(CategoryMapper.INSTANCE.toEntity(courseDto.getCategory()));
            course = courseRepository.save(course);
            return CourseMapper.INSTANCE.toDto(course);
        } else {
            return null; // или выбросить исключение
        }
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
