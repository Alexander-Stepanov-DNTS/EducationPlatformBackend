package ru.stepanov.EducationPlatform.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import ru.stepanov.EducationPlatform.models.*;
import ru.stepanov.EducationPlatform.models.EmbeddedId.StudentLessonId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class StudentLessonRepositoryTest {

    @Autowired
    private StudentLessonRepository studentLessonRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LessonRepository lessonRepository;

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

        Lesson lesson = new Lesson();
        lesson.setName("Test Lesson");
        lesson.setNumber(1);
        lesson.setCourseOrder(1);
        lesson.setCourse(course);
        lesson = lessonRepository.save(lesson);

        StudentLessonId studentLessonId = new StudentLessonId(user.getId(), lesson.getId());
        StudentLesson studentLesson = new StudentLesson();
        studentLesson.setId(studentLessonId);
        studentLesson.setStudent(user);
        studentLesson.setLesson(lesson);
        studentLesson.setCompletedDatetime(LocalDateTime.now());
        studentLesson = studentLessonRepository.save(studentLesson);

        assertNotNull(studentLesson.getId());
        Optional<StudentLesson> foundStudentLesson = studentLessonRepository.findById(studentLesson.getId());
        assertTrue(foundStudentLesson.isPresent());
        assertEquals(user.getId(), foundStudentLesson.get().getStudent().getId());
        assertEquals(lesson.getId(), foundStudentLesson.get().getLesson().getId());
    }
}