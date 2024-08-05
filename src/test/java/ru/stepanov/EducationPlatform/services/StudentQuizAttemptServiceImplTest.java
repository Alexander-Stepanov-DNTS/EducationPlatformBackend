package ru.stepanov.EducationPlatform.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.QuizDto;
import ru.stepanov.EducationPlatform.DTO.StudentQuizAttemptDto;
import ru.stepanov.EducationPlatform.DTO.UserDto;
import ru.stepanov.EducationPlatform.models.*;
import ru.stepanov.EducationPlatform.repositories.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class StudentQuizAttemptServiceImplTest {

    @Autowired
    private StudentQuizAttemptService studentQuizAttemptService;

    @Autowired
    private StudentQuizAttemptRepository studentQuizAttemptRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DirectionRepository directionRepository;

    private User savedUser;
    private Quiz savedQuiz;

    @BeforeEach
    public void setUp() {
        studentQuizAttemptRepository.deleteAll();
        quizRepository.deleteAll();
        userRepository.deleteAll();
        institutionRepository.deleteAll();
        roleRepository.deleteAll();
        courseRepository.deleteAll();
        categoryRepository.deleteAll();
        directionRepository.deleteAll();

        Institution institution = new Institution();
        institution.setName("Test Institution");
        institution.setType("University");
        Institution savedInstitution = institutionRepository.save(institution);

        Role role = new Role();
        role.setName("Student");
        Role savedRole = roleRepository.save(role);

        User user = new User();
        user.setLogin("Test User");
        user.setEmailAddress("testuser@example.com");
        user.setPassword("password");
        user.setSignupDate(LocalDate.now());
        user.setInstitution(savedInstitution);
        user.setRole(savedRole);
        savedUser = userRepository.save(user);

        Direction direction = new Direction();
        direction.setName("Test Direction");
        direction.setDescription("Test Direction Description");
        Direction savedDirection = directionRepository.save(direction);

        Category category = new Category();
        category.setName("Test Category");
        category.setDescription("Test Category Description");
        category.setDirection(savedDirection); // Устанавливаем direction
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
    }

    @Test
    @Transactional
    public void testCreateStudentQuizAttempt() {
        StudentQuizAttemptDto studentQuizAttemptDto = new StudentQuizAttemptDto();

        UserDto userDto = new UserDto();
        userDto.setId(savedUser.getId());
        studentQuizAttemptDto.setStudent(userDto);

        QuizDto quizDto = new QuizDto();
        quizDto.setId(savedQuiz.getId());
        studentQuizAttemptDto.setQuiz(quizDto);

        studentQuizAttemptDto.setScoreAchieved(85);

        StudentQuizAttemptDto createdStudentQuizAttempt = studentQuizAttemptService.createStudentQuizAttempt(studentQuizAttemptDto);

        assertNotNull(createdStudentQuizAttempt);
        assertNotNull(createdStudentQuizAttempt.getStudent().getId());
        assertNotNull(createdStudentQuizAttempt.getQuiz().getId());
        assertEquals(studentQuizAttemptDto.getStudent().getId(), createdStudentQuizAttempt.getStudent().getId());
        assertEquals(studentQuizAttemptDto.getQuiz().getId(), createdStudentQuizAttempt.getQuiz().getId());
        assertEquals(studentQuizAttemptDto.getScoreAchieved(), createdStudentQuizAttempt.getScoreAchieved());
        assertNotNull(createdStudentQuizAttempt.getAttemptDatetime());
    }

    @Test
    @Transactional
    public void testGetStudentQuizAttemptById() {
        StudentQuizAttemptDto studentQuizAttemptDto = new StudentQuizAttemptDto();

        UserDto userDto = new UserDto();
        userDto.setId(savedUser.getId());
        studentQuizAttemptDto.setStudent(userDto);

        QuizDto quizDto = new QuizDto();
        quizDto.setId(savedQuiz.getId());
        studentQuizAttemptDto.setQuiz(quizDto);

        studentQuizAttemptDto.setScoreAchieved(85);

        StudentQuizAttemptDto createdStudentQuizAttempt = studentQuizAttemptService.createStudentQuizAttempt(studentQuizAttemptDto);

        StudentQuizAttemptDto foundStudentQuizAttempt = studentQuizAttemptService.getStudentQuizAttemptById(
                createdStudentQuizAttempt.getStudent().getId(),
                createdStudentQuizAttempt.getQuiz().getId(),
                createdStudentQuizAttempt.getAttemptDatetime()
        );

        assertNotNull(foundStudentQuizAttempt);
        assertEquals(createdStudentQuizAttempt.getStudent().getId(), foundStudentQuizAttempt.getStudent().getId());
        assertEquals(createdStudentQuizAttempt.getQuiz().getId(), foundStudentQuizAttempt.getQuiz().getId());
        assertEquals(createdStudentQuizAttempt.getScoreAchieved(), foundStudentQuizAttempt.getScoreAchieved());
        assertEquals(createdStudentQuizAttempt.getAttemptDatetime(), foundStudentQuizAttempt.getAttemptDatetime());
    }

    @Test
    @Transactional
    public void testUpdateStudentQuizAttempt() {
        StudentQuizAttemptDto studentQuizAttemptDto = new StudentQuizAttemptDto();

        UserDto userDto = new UserDto();
        userDto.setId(savedUser.getId());
        studentQuizAttemptDto.setStudent(userDto);

        QuizDto quizDto = new QuizDto();
        quizDto.setId(savedQuiz.getId());
        studentQuizAttemptDto.setQuiz(quizDto);

        studentQuizAttemptDto.setScoreAchieved(85);

        StudentQuizAttemptDto createdStudentQuizAttempt = studentQuizAttemptService.createStudentQuizAttempt(studentQuizAttemptDto);

        createdStudentQuizAttempt.setScoreAchieved(95);

        StudentQuizAttemptDto updatedStudentQuizAttempt = studentQuizAttemptService.updateStudentQuizAttempt(
                createdStudentQuizAttempt.getStudent().getId(),
                createdStudentQuizAttempt.getQuiz().getId(),
                createdStudentQuizAttempt.getAttemptDatetime(),
                createdStudentQuizAttempt
        );

        assertNotNull(updatedStudentQuizAttempt);
        assertEquals(createdStudentQuizAttempt.getStudent().getId(), updatedStudentQuizAttempt.getStudent().getId());
        assertEquals(createdStudentQuizAttempt.getQuiz().getId(), updatedStudentQuizAttempt.getQuiz().getId());
        assertEquals(createdStudentQuizAttempt.getScoreAchieved(), updatedStudentQuizAttempt.getScoreAchieved());
        assertEquals(createdStudentQuizAttempt.getAttemptDatetime(), updatedStudentQuizAttempt.getAttemptDatetime());
    }

    @Test
    @Transactional
    public void testDeleteStudentQuizAttempt() {
        StudentQuizAttemptDto studentQuizAttemptDto = new StudentQuizAttemptDto();

        UserDto userDto = new UserDto();
        userDto.setId(savedUser.getId());
        studentQuizAttemptDto.setStudent(userDto);

        QuizDto quizDto = new QuizDto();
        quizDto.setId(savedQuiz.getId());
        studentQuizAttemptDto.setQuiz(quizDto);

        studentQuizAttemptDto.setScoreAchieved(85);

        StudentQuizAttemptDto createdStudentQuizAttempt = studentQuizAttemptService.createStudentQuizAttempt(studentQuizAttemptDto);

        studentQuizAttemptService.deleteStudentQuizAttempt(
                createdStudentQuizAttempt.getStudent().getId(),
                createdStudentQuizAttempt.getQuiz().getId(),
                createdStudentQuizAttempt.getAttemptDatetime()
        );

        StudentQuizAttemptDto foundStudentQuizAttempt = studentQuizAttemptService.getStudentQuizAttemptById(
                createdStudentQuizAttempt.getStudent().getId(),
                createdStudentQuizAttempt.getQuiz().getId(),
                createdStudentQuizAttempt.getAttemptDatetime()
        );

        assertNull(foundStudentQuizAttempt);
    }
}