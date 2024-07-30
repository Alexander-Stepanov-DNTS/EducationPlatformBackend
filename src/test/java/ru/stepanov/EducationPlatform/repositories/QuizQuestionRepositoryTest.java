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
import ru.stepanov.EducationPlatform.models.QuizQuestion;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class QuizQuestionRepositoryTest {

    @Autowired
    private QuizQuestionRepository quizQuestionRepository;

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
        quiz = quizRepository.save(quiz);

        QuizQuestion question = new QuizQuestion();
        question.setQuiz(quiz);
        question.setQuestionTitle("What is Java?");
        question.setManyAnswers(false);
        QuizQuestion savedQuestion = quizQuestionRepository.save(question);

        assertNotNull(savedQuestion.getId());
        List<QuizQuestion> foundQuestions = quizQuestionRepository.findByQuizId(quiz.getId());
        assertEquals(1, foundQuestions.size());
        assertEquals("What is Java?", foundQuestions.get(0).getQuestionTitle());
    }

    @Test
    public void testFindByQuizId() {
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
        quiz = quizRepository.save(quiz);

        QuizQuestion question1 = new QuizQuestion();
        question1.setQuiz(quiz);
        question1.setQuestionTitle("What is Java?");
        question1.setManyAnswers(false);
        quizQuestionRepository.save(question1);

        QuizQuestion question2 = new QuizQuestion();
        question2.setQuiz(quiz);
        question2.setQuestionTitle("Explain polymorphism.");
        question2.setManyAnswers(true);
        quizQuestionRepository.save(question2);

        List<QuizQuestion> foundQuestions = quizQuestionRepository.findByQuizId(quiz.getId());
        assertEquals(2, foundQuestions.size());
        assertTrue(foundQuestions.stream().anyMatch(q -> q.getQuestionTitle().equals("What is Java?")));
        assertTrue(foundQuestions.stream().anyMatch(q -> q.getQuestionTitle().equals("Explain polymorphism.")));
    }
}