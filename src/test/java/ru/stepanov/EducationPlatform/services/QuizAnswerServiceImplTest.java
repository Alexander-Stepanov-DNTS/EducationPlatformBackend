package ru.stepanov.EducationPlatform.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.QuizAnswerDto;
import ru.stepanov.EducationPlatform.DTO.QuizQuestionDto;
import ru.stepanov.EducationPlatform.models.*;
import ru.stepanov.EducationPlatform.repositories.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class QuizAnswerServiceImplTest {

    @Autowired
    private QuizAnswerService quizAnswerService;

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

    private QuizQuestion savedQuizQuestion;

    @BeforeEach
    public void setUp() {
        quizAnswerRepository.deleteAll();
        quizQuestionRepository.deleteAll();
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
        Course savedCourse = courseRepository.save(course);

        Quiz quiz = new Quiz();
        quiz.setTitle("Test Quiz");
        quiz.setDescription("Test Quiz Description");
        quiz.setCreatedDate(LocalDateTime.now());
        quiz.setIsActive(true);
        quiz.setDueDate(null);
        quiz.setReminderDate(null);
        quiz.setCourseOrder(1);
        quiz.setCourse(savedCourse);
        Quiz savedQuiz = quizRepository.save(quiz);

        QuizQuestion quizQuestion = new QuizQuestion();
        quizQuestion.setQuestionTitle("Test Question");
        quizQuestion.setManyAnswers(false);
        quizQuestion.setQuiz(savedQuiz);
        savedQuizQuestion = quizQuestionRepository.save(quizQuestion);
    }

    @Test
    @Transactional
    public void testCreateQuizAnswer() {
        QuizAnswerDto quizAnswerDto = new QuizAnswerDto();
        QuizQuestionDto questionDto = new QuizQuestionDto();
        questionDto.setId(savedQuizQuestion.getId());
        quizAnswerDto.setQuestion(questionDto);
        quizAnswerDto.setAnswerText("Test Answer");
        quizAnswerDto.setIsCorrect(false);

        QuizAnswerDto createdQuizAnswer = quizAnswerService.createQuizAnswer(quizAnswerDto);

        assertNotNull(createdQuizAnswer.getId());
        assertEquals(quizAnswerDto.getAnswerText(), createdQuizAnswer.getAnswerText());
        assertEquals(quizAnswerDto.getIsCorrect(), createdQuizAnswer.getIsCorrect());
        assertEquals(quizAnswerDto.getQuestion().getId(), createdQuizAnswer.getQuestion().getId());
    }

    @Test
    @Transactional
    public void testGetQuizAnswerById() {
        QuizAnswer answer = new QuizAnswer();
        answer.setQuestion(savedQuizQuestion);
        answer.setAnswerText("Test Answer");
        answer.setIsCorrect(false);
        QuizAnswer savedAnswer = quizAnswerRepository.save(answer);

        QuizAnswerDto foundAnswer = quizAnswerService.getQuizAnswerById(savedAnswer.getId());

        assertNotNull(foundAnswer);
        assertEquals(savedAnswer.getId(), foundAnswer.getId());
        assertEquals(savedAnswer.getAnswerText(), foundAnswer.getAnswerText());
        assertEquals(savedAnswer.getIsCorrect(), foundAnswer.getIsCorrect());
        assertEquals(savedAnswer.getQuestion().getId(), foundAnswer.getQuestion().getId());
    }

    @Test
    @Transactional
    public void testGetAllQuizAnswers() {
        QuizAnswer answer = new QuizAnswer();
        answer.setQuestion(savedQuizQuestion);
        answer.setAnswerText("Test Answer");
        answer.setIsCorrect(false);
        quizAnswerRepository.save(answer);

        List<QuizAnswerDto> answers = quizAnswerService.getAllQuizAnswers();

        assertFalse(answers.isEmpty());
        assertEquals(1, answers.size());
        assertEquals(answer.getAnswerText(), answers.get(0).getAnswerText());
    }

    @Test
    @Transactional
    public void testUpdateQuizAnswer() {
        QuizAnswer answer = new QuizAnswer();
        answer.setQuestion(savedQuizQuestion);
        answer.setAnswerText("Test Answer");
        answer.setIsCorrect(false);
        QuizAnswer savedAnswer = quizAnswerRepository.save(answer);

        QuizAnswerDto quizAnswerDto = new QuizAnswerDto();
        quizAnswerDto.setAnswerText("Updated Answer");
        quizAnswerDto.setIsCorrect(true);
        quizAnswerDto.setQuestion(new QuizQuestionDto());

        QuizAnswerDto updatedAnswer = quizAnswerService.updateQuizAnswer(savedAnswer.getId(), quizAnswerDto);

        assertNotNull(updatedAnswer);
        assertEquals(savedAnswer.getId(), updatedAnswer.getId());
        assertEquals(quizAnswerDto.getAnswerText(), updatedAnswer.getAnswerText());
        assertEquals(quizAnswerDto.getIsCorrect(), updatedAnswer.getIsCorrect());
    }

    @Test
    @Transactional
    public void testDeleteQuizAnswer() {
        QuizAnswer answer = new QuizAnswer();
        answer.setQuestion(savedQuizQuestion);
        answer.setAnswerText("Test Answer");
        answer.setIsCorrect(false);
        QuizAnswer savedAnswer = quizAnswerRepository.save(answer);

        quizAnswerService.deleteQuizAnswer(savedAnswer.getId());

        Optional<QuizAnswer> deletedAnswer = quizAnswerRepository.findById(savedAnswer.getId());
        assertFalse(deletedAnswer.isPresent());
    }
}