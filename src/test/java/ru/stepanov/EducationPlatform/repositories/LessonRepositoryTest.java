package ru.stepanov.EducationPlatform.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import ru.stepanov.EducationPlatform.models.Category;
import ru.stepanov.EducationPlatform.models.Course;
import ru.stepanov.EducationPlatform.models.Direction;
import ru.stepanov.EducationPlatform.models.Lesson;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class LessonRepositoryTest {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DirectionRepository directionRepository;

    @Test
    public void testSaveAndFindById() {
        Direction direction = new Direction();
        direction.setName("IT");
        direction.setDescription("Information Technology");
        direction = directionRepository.save(direction);

        Category category = new Category();
        category.setName("Programming");
        category.setDescription("All about programming");
        category.setDirection(direction);
        category = categoryRepository.save(category);

        Course course = new Course();
        course.setName("Java Basics");
        course.setDescription("Learn Java from scratch");
        course.setPrice(1000L);
        course.setIsProgressLimited(false);
        course.setPicture_url("java.png");
        course.setCategory(category);
        course = courseRepository.save(course);

        Lesson lesson = new Lesson();
        lesson.setName("Introduction to Java");
        lesson.setNumber(1);
        lesson.setCourseOrder(1);
        lesson.setCourse(course);
        lesson.setVideoUrl("http://example.com/video");
        lesson.setLessonDetails("Basic concepts of Java");
        Lesson savedLesson = lessonRepository.save(lesson);

        assertNotNull(savedLesson.getId());
        Optional<Lesson> foundLesson = lessonRepository.findById(savedLesson.getId());
        assertTrue(foundLesson.isPresent());
        assertEquals("Introduction to Java", foundLesson.get().getName());
    }

    @Test
    public void testFindByCourseId() {
        Direction direction = new Direction();
        direction.setName("IT");
        direction.setDescription("Information Technology");
        direction = directionRepository.save(direction);

        Category category = new Category();
        category.setName("Programming");
        category.setDescription("All about programming");
        category.setDirection(direction);
        category = categoryRepository.save(category);

        Course course = new Course();
        course.setName("Java Basics");
        course.setDescription("Learn Java from scratch");
        course.setPrice(1000L);
        course.setIsProgressLimited(false);
        course.setPicture_url("java.png");
        course.setCategory(category);
        course = courseRepository.save(course);

        Lesson lesson1 = new Lesson();
        lesson1.setName("Introduction to Java");
        lesson1.setNumber(1);
        lesson1.setCourseOrder(1);
        lesson1.setCourse(course);
        lesson1.setVideoUrl("http://example.com/video1");
        lesson1.setLessonDetails("Basic concepts of Java");
        lessonRepository.save(lesson1);

        Lesson lesson2 = new Lesson();
        lesson2.setName("Java Syntax");
        lesson2.setNumber(2);
        lesson2.setCourseOrder(2);
        lesson2.setCourse(course);
        lesson2.setVideoUrl("http://example.com/video2");
        lesson2.setLessonDetails("Understanding Java syntax");
        lessonRepository.save(lesson2);

        List<Lesson> lessons = lessonRepository.findByCourseId(course.getId());
        assertEquals(2, lessons.size());
        assertTrue(lessons.stream().anyMatch(l -> l.getName().equals("Introduction to Java")));
        assertTrue(lessons.stream().anyMatch(l -> l.getName().equals("Java Syntax")));
    }
}