package ru.stepanov.EducationPlatform.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.QuizQuestionDto;
import ru.stepanov.EducationPlatform.DTO.QuizDto;
import ru.stepanov.EducationPlatform.DTO.QuizAnswerDto;
import ru.stepanov.EducationPlatform.models.*;
import ru.stepanov.EducationPlatform.repositories.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class QuizQuestionServiceImplTest {

    @Autowired
    private QuizQuestionService quizQuestionService;

    @Autowired
    private QuizQuestionRepository quizQuestionRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizAnswerRepository quizAnswerRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DirectionRepository directionRepository;

    private Quiz savedQuiz;
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
        quiz.setCourseOrder(1);
        quiz.setCourse(savedCourse);
        savedQuiz = quizRepository.save(quiz);

        QuizQuestion quizQuestion = new QuizQuestion();
        quizQuestion.setQuiz(savedQuiz);
        quizQuestion.setQuestionTitle("Test Question");
        quizQuestion.setManyAnswers(false);
        savedQuizQuestion = quizQuestionRepository.save(quizQuestion);
    }

    @Test
    @Transactional
    public void testGetQuizQuestionById() {
        QuizQuestionDto foundQuestion = quizQuestionService.getQuizQuestionById(savedQuizQuestion.getId());
        assertNotNull(foundQuestion);
        assertEquals(savedQuizQuestion.getId(), foundQuestion.getId());
        assertEquals(savedQuizQuestion.getQuestionTitle(), foundQuestion.getQuestionTitle());
        assertEquals(savedQuizQuestion.getManyAnswers(), foundQuestion.getManyAnswers());
        assertEquals(savedQuizQuestion.getQuiz().getId(), foundQuestion.getQuiz().getId());
    }

    @Test
    @Transactional
    public void testGetAllQuizQuestions() {
        List<QuizQuestionDto> questions = quizQuestionService.getAllQuizQuestions();
        assertFalse(questions.isEmpty());
        assertEquals(1, questions.size());
        assertEquals(savedQuizQuestion.getId(), questions.get(0).getId());
    }

    @Test
    @Transactional
    public void testCreateQuizQuestion() {
        QuizDto quizDto = new QuizDto();
        quizDto.setId(savedQuiz.getId());

        QuizQuestionDto questionDto = new QuizQuestionDto();
        questionDto.setQuiz(quizDto);
        questionDto.setQuestionTitle("New Question");
        questionDto.setManyAnswers(true);

        QuizQuestionDto createdQuestion = quizQuestionService.createQuizQuestion(questionDto);
        assertNotNull(createdQuestion.getId());
        assertEquals(questionDto.getQuestionTitle(), createdQuestion.getQuestionTitle());
        assertEquals(questionDto.getManyAnswers(), createdQuestion.getManyAnswers());
        assertEquals(questionDto.getQuiz().getId(), createdQuestion.getQuiz().getId());
    }

    @Test
    @Transactional
    public void testUpdateQuizQuestion() {
        QuizDto quizDto = new QuizDto();
        quizDto.setId(savedQuiz.getId());

        QuizQuestionDto questionDto = new QuizQuestionDto();
        questionDto.setQuiz(quizDto);
        questionDto.setQuestionTitle("Updated Question");
        questionDto.setManyAnswers(true);

        QuizQuestionDto updatedQuestion = quizQuestionService.updateQuizQuestion(savedQuizQuestion.getId(), questionDto);
        assertNotNull(updatedQuestion);
        assertEquals(savedQuizQuestion.getId(), updatedQuestion.getId());
        assertEquals(questionDto.getQuestionTitle(), updatedQuestion.getQuestionTitle());
        assertEquals(questionDto.getManyAnswers(), updatedQuestion.getManyAnswers());
        assertEquals(questionDto.getQuiz().getId(), updatedQuestion.getQuiz().getId());
    }

    @Test
    @Transactional
    public void testDeleteQuizQuestion() {
        quizQuestionService.deleteQuizQuestion(savedQuizQuestion.getId());
        Optional<QuizQuestion> deletedQuestion = quizQuestionRepository.findById(savedQuizQuestion.getId());
        assertFalse(deletedQuestion.isPresent());
    }

    @Test
    @Transactional
    public void testGetQuestionAnswers() {
        QuizAnswer answer = new QuizAnswer();
        answer.setQuestion(savedQuizQuestion);
        answer.setAnswerText("Test Answer");
        answer.setIsCorrect(false); // Установите значение для свойства isCorrect
        quizAnswerRepository.save(answer);

        List<QuizAnswerDto> answers = quizQuestionService.getQuestionAnswers(savedQuizQuestion.getId());
        assertFalse(answers.isEmpty());
        assertEquals(1, answers.size());
        assertEquals(answer.getId(), answers.get(0).getId());
        assertEquals(answer.getAnswerText(), answers.get(0).getAnswerText());
        assertEquals(answer.getQuestion().getId(), answers.get(0).getQuestion().getId());
    }
}