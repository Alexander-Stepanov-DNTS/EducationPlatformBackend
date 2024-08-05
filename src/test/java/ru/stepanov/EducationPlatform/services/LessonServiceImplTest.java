package ru.stepanov.EducationPlatform.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.CourseDto;
import ru.stepanov.EducationPlatform.DTO.LessonDto;
import ru.stepanov.EducationPlatform.models.Category;
import ru.stepanov.EducationPlatform.models.Course;
import ru.stepanov.EducationPlatform.models.Direction;
import ru.stepanov.EducationPlatform.models.Lesson;
import ru.stepanov.EducationPlatform.repositories.CategoryRepository;
import ru.stepanov.EducationPlatform.repositories.CourseRepository;
import ru.stepanov.EducationPlatform.repositories.DirectionRepository;
import ru.stepanov.EducationPlatform.repositories.LessonRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class LessonServiceImplTest {

    @Autowired
    private LessonService lessonService;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DirectionRepository directionRepository;

    private Course savedCourse;
    private Lesson savedLesson;

    @BeforeEach
    public void setUp() {
        lessonRepository.deleteAll();
        courseRepository.deleteAll();
        categoryRepository.deleteAll();
        directionRepository.deleteAll();

        Direction direction = new Direction();
        direction.setName("Test Direction");
        direction.setDescription("Test Direction Description");
        Direction savedDirection = directionRepository.save(direction);

        Category category = new Category();
        category.setName("Test Category");
        category.setDescription("Test Category Description");
        category.setDirection(savedDirection);
        Category savedCategory = categoryRepository.save(category);

        Course course = new Course();
        course.setName("Test Course");
        course.setDescription("Test Course Description");
        course.setPrice(1000L);
        course.setIsProgressLimited(true);
        course.setPicture_url("http://testurl.com/picture");
        course.setCategory(savedCategory);
        savedCourse = courseRepository.save(course);

        Lesson lesson = new Lesson();
        lesson.setName("Test Lesson");
        lesson.setNumber(1);
        lesson.setVideoUrl("http://testurl.com/video");
        lesson.setLessonDetails("Test Lesson Details");
        lesson.setCourseOrder(1);
        lesson.setCourse(savedCourse);
        savedLesson = lessonRepository.save(lesson);
    }

    @Test
    @Transactional
    public void testGetLessonById() {
        LessonDto foundLesson = lessonService.getLessonById(savedLesson.getId());
        assertNotNull(foundLesson);
        assertEquals(savedLesson.getId(), foundLesson.getId());
        assertEquals(savedLesson.getName(), foundLesson.getName());
        assertEquals(savedLesson.getNumber(), foundLesson.getNumber());
        assertEquals(savedLesson.getVideoUrl(), foundLesson.getVideoUrl());
        assertEquals(savedLesson.getLessonDetails(), foundLesson.getLessonDetails());
        assertEquals(savedLesson.getCourseOrder(), foundLesson.getCourseOrder());
        assertEquals(savedLesson.getCourse().getId(), foundLesson.getCourse().getId());
    }

    @Test
    @Transactional
    public void testGetAllLessons() {
        List<LessonDto> lessons = lessonService.getAllLessons();
        assertFalse(lessons.isEmpty());
        assertEquals(1, lessons.size());
        assertEquals(savedLesson.getId(), lessons.get(0).getId());
    }

    @Test
    @Transactional
    public void testGetLessonsFromCourse() {
        List<LessonDto> lessons = lessonService.getLessonsFromCourse(savedCourse.getId());
        assertFalse(lessons.isEmpty());
        assertEquals(1, lessons.size());
        assertEquals(savedLesson.getId(), lessons.get(0).getId());
        assertEquals(savedCourse.getId(), lessons.get(0).getCourse().getId());
    }

    @Test
    @Transactional
    public void testCreateLesson() {
        LessonDto lessonDto = new LessonDto();
        lessonDto.setName("New Lesson");
        lessonDto.setNumber(2);
        lessonDto.setVideoUrl("http://testurl.com/newvideo");
        lessonDto.setLessonDetails("New Lesson Details");
        lessonDto.setCourseOrder(2);
        CourseDto courseDto = new CourseDto();
        courseDto.setId(savedCourse.getId());
        lessonDto.setCourse(courseDto);

        LessonDto createdLesson = lessonService.createLesson(lessonDto);
        assertNotNull(createdLesson.getId());
        assertEquals(lessonDto.getName(), createdLesson.getName());
        assertEquals(lessonDto.getNumber(), createdLesson.getNumber());
        assertEquals(lessonDto.getVideoUrl(), createdLesson.getVideoUrl());
        assertEquals(lessonDto.getLessonDetails(), createdLesson.getLessonDetails());
        assertEquals(lessonDto.getCourseOrder(), createdLesson.getCourseOrder());
        assertEquals(lessonDto.getCourse().getId(), createdLesson.getCourse().getId());
    }

    @Test
    @Transactional
    public void testUpdateLesson() {
        LessonDto lessonDto = new LessonDto();
        lessonDto.setName("Updated Lesson");
        lessonDto.setNumber(3);
        lessonDto.setVideoUrl("http://testurl.com/updatedvideo");
        lessonDto.setLessonDetails("Updated Lesson Details");
        lessonDto.setCourseOrder(3);
        CourseDto courseDto = new CourseDto();
        courseDto.setId(savedCourse.getId());
        lessonDto.setCourse(courseDto);

        LessonDto updatedLesson = lessonService.updateLesson(savedLesson.getId(), lessonDto);
        assertNotNull(updatedLesson);
        assertEquals(savedLesson.getId(), updatedLesson.getId());
        assertEquals(lessonDto.getName(), updatedLesson.getName());
        assertEquals(lessonDto.getNumber(), updatedLesson.getNumber());
        assertEquals(lessonDto.getVideoUrl(), updatedLesson.getVideoUrl());
        assertEquals(lessonDto.getLessonDetails(), updatedLesson.getLessonDetails());
        assertEquals(lessonDto.getCourseOrder(), updatedLesson.getCourseOrder());
        assertEquals(lessonDto.getCourse().getId(), updatedLesson.getCourse().getId());
    }

    @Test
    @Transactional
    public void testDeleteLesson() {
        lessonService.deleteLesson(savedLesson.getId());
        Optional<Lesson> deletedLesson = lessonRepository.findById(savedLesson.getId());
        assertFalse(deletedLesson.isPresent());
    }
}