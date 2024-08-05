package ru.stepanov.EducationPlatform.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.CourseDto;
import ru.stepanov.EducationPlatform.DTO.QuizDto;
import ru.stepanov.EducationPlatform.models.Category;
import ru.stepanov.EducationPlatform.models.Course;
import ru.stepanov.EducationPlatform.models.Direction;
import ru.stepanov.EducationPlatform.models.Quiz;
import ru.stepanov.EducationPlatform.repositories.CategoryRepository;
import ru.stepanov.EducationPlatform.repositories.CourseRepository;
import ru.stepanov.EducationPlatform.repositories.DirectionRepository;
import ru.stepanov.EducationPlatform.repositories.QuizRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class QuizServiceImplTest {

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DirectionRepository directionRepository;

    private Course savedCourse;
    private Quiz savedQuiz;

    @BeforeEach
    public void setUp() {
        quizRepository.deleteAll();
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

        Quiz quiz = new Quiz();
        quiz.setTitle("Test Quiz");
        quiz.setDescription("Test Quiz Description");
        quiz.setCreatedDate(LocalDateTime.now());
        quiz.setIsActive(true);
        quiz.setDueDate(new Date());
        quiz.setReminderDate(new Date());
        quiz.setCourseOrder(1);
        quiz.setCourse(savedCourse);
        savedQuiz = quizRepository.save(quiz);
    }

    @Test
    @Transactional
    public void testGetQuizById() {
        QuizDto foundQuiz = quizService.getQuizById(savedQuiz.getId());
        assertNotNull(foundQuiz);
        assertEquals(savedQuiz.getId(), foundQuiz.getId());
        assertEquals(savedQuiz.getTitle(), foundQuiz.getTitle());
        assertEquals(savedQuiz.getDescription(), foundQuiz.getDescription());
        assertEquals(savedQuiz.getCreatedDate(), foundQuiz.getCreatedDate());
        assertEquals(savedQuiz.getIsActive(), foundQuiz.getIsActive());
        assertEquals(savedQuiz.getDueDate(), foundQuiz.getDueDate());
        assertEquals(savedQuiz.getReminderDate(), foundQuiz.getReminderDate());
        assertEquals(savedQuiz.getCourseOrder(), foundQuiz.getCourseOrder());
        assertEquals(savedQuiz.getCourse().getId(), foundQuiz.getCourse().getId());
    }

    @Test
    @Transactional
    public void testGetAllQuizzes() {
        List<QuizDto> quizzes = quizService.getAllQuizzes();
        assertFalse(quizzes.isEmpty());
        assertEquals(1, quizzes.size());
        assertEquals(savedQuiz.getId(), quizzes.get(0).getId());
    }

    @Test
    @Transactional
    public void testGetQuizzesFromCourse() {
        List<QuizDto> quizzes = quizService.getQuizzesFromCourse(savedCourse.getId());
        assertFalse(quizzes.isEmpty());
        assertEquals(1, quizzes.size());
        assertEquals(savedQuiz.getId(), quizzes.get(0).getId());
        assertEquals(savedCourse.getId(), quizzes.get(0).getCourse().getId());
    }

    @Test
    @Transactional
    public void testCreateQuiz() {
        QuizDto quizDto = new QuizDto();
        quizDto.setTitle("New Quiz");
        quizDto.setDescription("New Quiz Description");
        quizDto.setCreatedDate(LocalDateTime.now());
        quizDto.setIsActive(true);
        quizDto.setDueDate(new Date());
        quizDto.setReminderDate(new Date());
        quizDto.setCourseOrder(2);
        CourseDto courseDto = new CourseDto();
        courseDto.setId(savedCourse.getId());
        quizDto.setCourse(courseDto);

        QuizDto createdQuiz = quizService.createQuiz(quizDto);
        assertNotNull(createdQuiz.getId());
        assertEquals(quizDto.getTitle(), createdQuiz.getTitle());
        assertEquals(quizDto.getDescription(), createdQuiz.getDescription());
        assertEquals(quizDto.getCreatedDate(), createdQuiz.getCreatedDate());
        assertEquals(quizDto.getIsActive(), createdQuiz.getIsActive());
        assertEquals(quizDto.getDueDate(), createdQuiz.getDueDate());
        assertEquals(quizDto.getReminderDate(), createdQuiz.getReminderDate());
        assertEquals(quizDto.getCourseOrder(), createdQuiz.getCourseOrder());
        assertEquals(quizDto.getCourse().getId(), createdQuiz.getCourse().getId());
    }

    @Test
    @Transactional
    public void testUpdateQuiz() {
        QuizDto quizDto = new QuizDto();
        quizDto.setTitle("Updated Quiz");
        quizDto.setDescription("Updated Quiz Description");
        quizDto.setCreatedDate(LocalDateTime.now());
        quizDto.setIsActive(true);
        quizDto.setDueDate(new Date());
        quizDto.setReminderDate(new Date());
        quizDto.setCourseOrder(3);
        CourseDto courseDto = new CourseDto();
        courseDto.setId(savedCourse.getId());
        quizDto.setCourse(courseDto);

        QuizDto updatedQuiz = quizService.updateQuiz(savedQuiz.getId(), quizDto);
        assertNotNull(updatedQuiz);
        assertEquals(savedQuiz.getId(), updatedQuiz.getId());
        assertEquals(quizDto.getTitle(), updatedQuiz.getTitle());
        assertEquals(quizDto.getDescription(), updatedQuiz.getDescription());
        assertEquals(quizDto.getCreatedDate(), updatedQuiz.getCreatedDate());
        assertEquals(quizDto.getIsActive(), updatedQuiz.getIsActive());
        assertEquals(quizDto.getDueDate(), updatedQuiz.getDueDate());
        assertEquals(quizDto.getReminderDate(), updatedQuiz.getReminderDate());
        assertEquals(quizDto.getCourseOrder(), updatedQuiz.getCourseOrder());
        assertEquals(quizDto.getCourse().getId(), updatedQuiz.getCourse().getId());
    }

    @Test
    @Transactional
    public void testDeleteQuiz() {
        quizService.deleteQuiz(savedQuiz.getId());
        Optional<Quiz> deletedQuiz = quizRepository.findById(savedQuiz.getId());
        assertFalse(deletedQuiz.isPresent());
    }
}