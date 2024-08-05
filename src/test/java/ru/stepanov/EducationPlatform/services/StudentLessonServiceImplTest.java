package ru.stepanov.EducationPlatform.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.LessonDto;
import ru.stepanov.EducationPlatform.DTO.StudentLessonDto;
import ru.stepanov.EducationPlatform.DTO.UserDto;
import ru.stepanov.EducationPlatform.mappers.StudentLessonMapper;
import ru.stepanov.EducationPlatform.models.*;
import ru.stepanov.EducationPlatform.models.EmbeddedId.StudentLessonId;
import ru.stepanov.EducationPlatform.repositories.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class StudentLessonServiceImplTest {

    @Autowired
    private StudentLessonService studentLessonService;

    @Autowired
    private StudentLessonRepository studentLessonRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DirectionRepository directionRepository;

    private User savedUser;
    private Lesson savedLesson;
    private StudentLesson savedStudentLesson;

    @BeforeEach
    public void setUp() {
        studentLessonRepository.deleteAll();
        userRepository.deleteAll();
        lessonRepository.deleteAll();
        courseRepository.deleteAll();
        categoryRepository.deleteAll();
        directionRepository.deleteAll();
        institutionRepository.deleteAll();
        roleRepository.deleteAll();

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

        Lesson lesson = new Lesson();
        lesson.setName("Test Lesson");
        lesson.setNumber(1);
        lesson.setVideoUrl("http://testurl.com/video");
        lesson.setLessonDetails("Test Lesson Details");
        lesson.setCourseOrder(1);
        lesson.setCourse(savedCourse);
        savedLesson = lessonRepository.save(lesson);

        Institution institution = new Institution();
        institution.setName("Test Institution");
        institution.setType("University");
        Institution savedInstitution = institutionRepository.save(institution);

        Role role = new Role();
        role.setName("ROLE_USER");
        Role savedRole = roleRepository.save(role);

        User user = new User();
        user.setEmailAddress("testuser@example.com");
        user.setPassword("password");
        user.setSignupDate(LocalDate.now());
        user.setLogin("testuser");
        user.setInstitution(savedInstitution);
        user.setRole(savedRole);
        savedUser = userRepository.save(user);

        StudentLesson studentLesson = new StudentLesson();
        StudentLessonId studentLessonId = new StudentLessonId(savedUser.getId(), savedLesson.getId());
        studentLesson.setId(studentLessonId);
        studentLesson.setStudent(savedUser);
        studentLesson.setLesson(savedLesson);
        studentLesson.setCompletedDatetime(LocalDateTime.now());
        savedStudentLesson = studentLessonRepository.save(studentLesson);
    }

    @Test
    @Transactional
    public void testGetStudentLessonById() {
        StudentLessonDto foundStudentLesson = studentLessonService.getStudentLessonById(savedUser.getId(), savedLesson.getId());
        assertNotNull(foundStudentLesson);
        assertEquals(savedStudentLesson.getStudent().getId(), foundStudentLesson.getStudent().getId());
        assertEquals(savedStudentLesson.getLesson().getId(), foundStudentLesson.getLesson().getId());
        assertEquals(savedStudentLesson.getCompletedDatetime(), foundStudentLesson.getCompletedDatetime());
    }

    @Test
    @Transactional
    public void testGetAllStudentLessons() {
        List<StudentLessonDto> studentLessons = studentLessonService.getAllStudentLessons();
        assertFalse(studentLessons.isEmpty());
        assertEquals(1, studentLessons.size());
        assertEquals(savedStudentLesson.getStudent().getId(), studentLessons.get(0).getStudent().getId());
        assertEquals(savedStudentLesson.getLesson().getId(), studentLessons.get(0).getLesson().getId());
    }

    @Test
    @Transactional
    public void testCreateStudentLesson() {
        StudentLessonDto studentLessonDto = new StudentLessonDto();

        UserDto userDto = new UserDto();
        userDto.setId(savedUser.getId());
        studentLessonDto.setStudent(userDto);

        LessonDto lessonDto = new LessonDto();
        lessonDto.setId(savedLesson.getId());
        studentLessonDto.setLesson(lessonDto);

        studentLessonDto.setCompletedDatetime(LocalDateTime.now());

        StudentLesson studentLesson = StudentLessonMapper.INSTANCE.toEntity(studentLessonDto);
        studentLesson.setId(new StudentLessonId(savedUser.getId(), savedLesson.getId()));

        StudentLesson savedStudentLesson = studentLessonRepository.save(studentLesson);
        StudentLessonDto createdStudentLesson = StudentLessonMapper.INSTANCE.toDto(savedStudentLesson);

        assertNotNull(createdStudentLesson.getStudent().getId());
        assertEquals(studentLessonDto.getStudent().getId(), createdStudentLesson.getStudent().getId());
        assertEquals(studentLessonDto.getLesson().getId(), createdStudentLesson.getLesson().getId());
        assertEquals(studentLessonDto.getCompletedDatetime(), createdStudentLesson.getCompletedDatetime());
    }

    @Test
    @Transactional
    public void testUpdateStudentLesson() {
        StudentLessonDto studentLessonDto = new StudentLessonDto();
        studentLessonDto.setCompletedDatetime(LocalDateTime.now().plusDays(1));

        UserDto userDto = new UserDto();
        userDto.setId(savedUser.getId());
        studentLessonDto.setStudent(userDto);

        LessonDto lessonDto = new LessonDto();
        lessonDto.setId(savedLesson.getId());
        studentLessonDto.setLesson(lessonDto);

        StudentLessonDto updatedStudentLesson = studentLessonService.updateStudentLesson(savedUser.getId(), savedLesson.getId(), studentLessonDto);
        assertNotNull(updatedStudentLesson);
        assertEquals(savedStudentLesson.getStudent().getId(), updatedStudentLesson.getStudent().getId());
        assertEquals(savedStudentLesson.getLesson().getId(), updatedStudentLesson.getLesson().getId());
        assertEquals(studentLessonDto.getCompletedDatetime(), updatedStudentLesson.getCompletedDatetime());
    }

    @Test
    @Transactional
    public void testDeleteStudentLesson() {
        studentLessonService.deleteStudentLesson(savedUser.getId(), savedLesson.getId());
        Optional<StudentLesson> deletedStudentLesson = studentLessonRepository.findById(new StudentLessonId(savedUser.getId(), savedLesson.getId()));
        assertFalse(deletedStudentLesson.isPresent());
    }
}