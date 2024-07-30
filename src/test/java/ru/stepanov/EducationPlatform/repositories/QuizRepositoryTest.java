package ru.stepanov.EducationPlatform.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import ru.stepanov.EducationPlatform.models.Category;
import ru.stepanov.EducationPlatform.models.Course;
import ru.stepanov.EducationPlatform.models.Direction;
import ru.stepanov.EducationPlatform.models.Quiz;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class QuizRepositoryTest {

    @Autowired
    private QuizRepository quizRepository;

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

        Quiz quiz = new Quiz();
        quiz.setTitle("Java Basics Quiz");
        quiz.setDescription("A quiz on the basics of Java");
        quiz.setCreatedDate(LocalDateTime.now());
        quiz.setIsActive(true);
        quiz.setCourseOrder(1);
        quiz.setCourse(course);
        Quiz savedQuiz = quizRepository.save(quiz);

        assertNotNull(savedQuiz.getId());
        Optional<Quiz> foundQuiz = quizRepository.findById(savedQuiz.getId());
        assertTrue(foundQuiz.isPresent());
        assertEquals("Java Basics Quiz", foundQuiz.get().getTitle());
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

        Quiz quiz1 = new Quiz();
        quiz1.setTitle("Java Basics Quiz 1");
        quiz1.setDescription("A quiz on the basics of Java");
        quiz1.setCreatedDate(LocalDateTime.now());
        quiz1.setIsActive(true);
        quiz1.setCourseOrder(1);
        quiz1.setCourse(course);
        quizRepository.save(quiz1);

        Quiz quiz2 = new Quiz();
        quiz2.setTitle("Java Basics Quiz 2");
        quiz2.setDescription("A quiz on Java syntax");
        quiz2.setCreatedDate(LocalDateTime.now());
        quiz2.setIsActive(true);
        quiz2.setCourseOrder(2);
        quiz2.setCourse(course);
        quizRepository.save(quiz2);

        List<Quiz> quizzes = quizRepository.findByCourseId(course.getId());
        assertEquals(2, quizzes.size());
        assertTrue(quizzes.stream().anyMatch(q -> q.getTitle().equals("Java Basics Quiz 1")));
        assertTrue(quizzes.stream().anyMatch(q -> q.getTitle().equals("Java Basics Quiz 2")));
    }
}