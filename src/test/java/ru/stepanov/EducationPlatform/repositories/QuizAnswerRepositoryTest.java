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
import ru.stepanov.EducationPlatform.models.QuizAnswer;
import ru.stepanov.EducationPlatform.models.QuizQuestion;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class QuizAnswerRepositoryTest {

    @Autowired
    private QuizAnswerRepository quizAnswerRepository;

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
        question = quizQuestionRepository.save(question);

        QuizAnswer answer = new QuizAnswer();
        answer.setQuestion(question);
        answer.setAnswerText("A programming language");
        answer.setIsCorrect(true);
        QuizAnswer savedAnswer = quizAnswerRepository.save(answer);

        assertNotNull(savedAnswer.getId());
        List<QuizAnswer> foundAnswers = quizAnswerRepository.findByQuestion_Id(question.getId());
        assertEquals(1, foundAnswers.size());
        assertEquals("A programming language", foundAnswers.get(0).getAnswerText());
    }

    @Test
    public void testFindByQuestionId() {
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
        question = quizQuestionRepository.save(question);

        QuizAnswer answer1 = new QuizAnswer();
        answer1.setQuestion(question);
        answer1.setAnswerText("A programming language");
        answer1.setIsCorrect(true);
        quizAnswerRepository.save(answer1);

        QuizAnswer answer2 = new QuizAnswer();
        answer2.setQuestion(question);
        answer2.setAnswerText("An island");
        answer2.setIsCorrect(false);
        quizAnswerRepository.save(answer2);

        List<QuizAnswer> foundAnswers = quizAnswerRepository.findByQuestion_Id(question.getId());
        assertEquals(2, foundAnswers.size());
        assertTrue(foundAnswers.stream().anyMatch(a -> a.getAnswerText().equals("A programming language")));
        assertTrue(foundAnswers.stream().anyMatch(a -> a.getAnswerText().equals("An island")));
    }
}