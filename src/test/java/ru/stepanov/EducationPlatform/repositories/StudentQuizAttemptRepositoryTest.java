package ru.stepanov.EducationPlatform.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import ru.stepanov.EducationPlatform.models.*;
import ru.stepanov.EducationPlatform.models.EmbeddedId.StudentQuizAttemptId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class StudentQuizAttemptRepositoryTest {

    @Autowired
    private StudentQuizAttemptRepository studentQuizAttemptRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DirectionRepository directionRepository;

    @Test
    public void testSaveAndFindById() {
        Institution institution = new Institution();
        institution.setName("Test Institution");
        institution.setType("University");
        institution = institutionRepository.save(institution);

        Role role = new Role();
        role.setName("ROLE_USER");
        role = roleRepository.save(role);

        User user = new User();
        user.setEmailAddress("test@example.com");
        user.setPassword("password");
        user.setSignupDate(LocalDate.now());
        user.setLogin("testUser");
        user.setRole(role);
        user.setInstitution(institution);
        user = userRepository.save(user);

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
        course.setName("Test Course");
        course.setDescription("Test Description");
        course.setPrice(100L);
        course.setIsProgressLimited(true);
        course.setPicture_url("http://example.com/pic.jpg");
        course.setCategory(category);
        course = courseRepository.save(course);

        Quiz quiz = new Quiz();
        quiz.setTitle("Test Quiz");
        quiz.setDescription("Test Quiz Description");
        quiz.setCreatedDate(LocalDateTime.now());
        quiz.setIsActive(true);
        quiz.setCourseOrder(1);
        quiz.setCourse(course);
        quiz = quizRepository.save(quiz);

        StudentQuizAttemptId studentQuizAttemptId = new StudentQuizAttemptId(user.getId(), quiz.getId(), LocalDateTime.now());
        StudentQuizAttempt studentQuizAttempt = new StudentQuizAttempt();
        studentQuizAttempt.setId(studentQuizAttemptId);
        studentQuizAttempt.setStudent(user);
        studentQuizAttempt.setQuiz(quiz);
        studentQuizAttempt.setAttemptDatetime(LocalDateTime.now());
        studentQuizAttempt.setScoreAchieved(95);
        studentQuizAttempt = studentQuizAttemptRepository.save(studentQuizAttempt);

        assertNotNull(studentQuizAttempt.getId());
        Optional<StudentQuizAttempt> foundStudentQuizAttempt = studentQuizAttemptRepository.findById(studentQuizAttempt.getId());
        assertTrue(foundStudentQuizAttempt.isPresent());
        assertEquals(user.getId(), foundStudentQuizAttempt.get().getStudent().getId());
        assertEquals(quiz.getId(), foundStudentQuizAttempt.get().getQuiz().getId());
    }
}