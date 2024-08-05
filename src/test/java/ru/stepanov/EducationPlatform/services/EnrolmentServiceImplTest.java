package ru.stepanov.EducationPlatform.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.EnrolmentDto;
import ru.stepanov.EducationPlatform.DTO.CourseDto;
import ru.stepanov.EducationPlatform.DTO.UserDto;
import ru.stepanov.EducationPlatform.models.*;
import ru.stepanov.EducationPlatform.models.EmbeddedId.EnrolmentId;
import ru.stepanov.EducationPlatform.repositories.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class EnrolmentServiceImplTest {

    @Autowired
    private EnrolmentService enrolmentService;

    @Autowired
    private EnrolmentRepository enrolmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DirectionRepository directionRepository;

    private User savedUser;
    private Course savedCourse;

    @BeforeEach
    public void setUp() {
        enrolmentRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        institutionRepository.deleteAll();
        courseRepository.deleteAll();
        categoryRepository.deleteAll();
        directionRepository.deleteAll();

        Role role = new Role();
        role.setName("Test Role");
        roleRepository.save(role);

        Institution institution = new Institution();
        institution.setName("Test Institution");
        institution.setType("University");
        institutionRepository.save(institution);

        User user = new User();
        user.setEmailAddress("testuser@example.com");
        user.setPassword("password");
        user.setSignupDate(LocalDateTime.now().toLocalDate());
        user.setLogin("testuser");
        user.setRole(role);
        user.setInstitution(institution);
        savedUser = userRepository.save(user);

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
    }

    @Test
    @Transactional
    public void testCreateEnrolment() {
        CourseDto courseDto = new CourseDto();
        courseDto.setId(savedCourse.getId());

        UserDto userDto = new UserDto();
        userDto.setId(savedUser.getId());

        EnrolmentDto enrolmentDto = new EnrolmentDto();
        enrolmentDto.setCourse(courseDto);
        enrolmentDto.setStudent(userDto);
        enrolmentDto.setEnrolmentDatetime(LocalDateTime.now());
        enrolmentDto.setCompletedDatetime(null);
        enrolmentDto.setIsAuthor(false);

        EnrolmentDto createdEnrolment = enrolmentService.createEnrolment(enrolmentDto);

        assertNotNull(createdEnrolment);
        assertNotNull(createdEnrolment.getCourse().getId());
        assertNotNull(createdEnrolment.getStudent().getId());
        assertEquals(enrolmentDto.getEnrolmentDatetime(), createdEnrolment.getEnrolmentDatetime());
        assertEquals(enrolmentDto.getIsAuthor(), createdEnrolment.getIsAuthor());
    }


    @Test
    @Transactional
    public void testGetEnrolmentById() {
        EnrolmentId enrolmentId = new EnrolmentId(savedCourse.getId(), savedUser.getId());
        Enrolment enrolment = new Enrolment();
        enrolment.setId(enrolmentId);
        enrolment.setCourse(savedCourse);
        enrolment.setStudent(savedUser);
        enrolment.setEnrolmentDatetime(LocalDateTime.now());
        enrolment.setIsAuthor(false);
        enrolmentRepository.save(enrolment);

        EnrolmentDto foundEnrolment = enrolmentService.getEnrolmentById(savedCourse.getId(), savedUser.getId());

        assertNotNull(foundEnrolment);
        assertEquals(savedCourse.getId(), foundEnrolment.getCourse().getId());
        assertEquals(savedUser.getId(), foundEnrolment.getStudent().getId());
    }

    @Test
    @Transactional
    public void testUpdateEnrolment() {
        // Создание и сохранение начального Enrolment
        EnrolmentId enrolmentId = new EnrolmentId(savedCourse.getId(), savedUser.getId());
        Enrolment enrolment = new Enrolment();
        enrolment.setId(enrolmentId);
        enrolment.setCourse(savedCourse);
        enrolment.setStudent(savedUser);
        enrolment.setEnrolmentDatetime(LocalDateTime.now());
        enrolment.setIsAuthor(false);
        enrolmentRepository.save(enrolment);

        // Создание DTO для обновления
        EnrolmentDto enrolmentDto = new EnrolmentDto();

        // Создание и инициализация CourseDto
        CourseDto courseDto = new CourseDto();
        courseDto.setId(savedCourse.getId());
        enrolmentDto.setCourse(courseDto);

        // Создание и инициализация UserDto
        UserDto userDto = new UserDto();
        userDto.setId(savedUser.getId());
        enrolmentDto.setStudent(userDto);

        enrolmentDto.setEnrolmentDatetime(LocalDateTime.now().plusDays(1));
        enrolmentDto.setCompletedDatetime(LocalDateTime.now().plusDays(2));
        enrolmentDto.setIsAuthor(true);

        // Обновление записи Enrolment через сервис
        EnrolmentDto updatedEnrolment = enrolmentService.updateEnrolment(savedCourse.getId(), savedUser.getId(), enrolmentDto);

        // Проверка результатов
        assertNotNull(updatedEnrolment);
        assertEquals(enrolmentDto.getEnrolmentDatetime(), updatedEnrolment.getEnrolmentDatetime());
        assertEquals(enrolmentDto.getCompletedDatetime(), updatedEnrolment.getCompletedDatetime());
        assertEquals(enrolmentDto.getIsAuthor(), updatedEnrolment.getIsAuthor());
    }

    @Test
    @Transactional
    public void testDeleteEnrolment() {
        EnrolmentId enrolmentId = new EnrolmentId(savedCourse.getId(), savedUser.getId());
        Enrolment enrolment = new Enrolment();
        enrolment.setId(enrolmentId);
        enrolment.setCourse(savedCourse);
        enrolment.setStudent(savedUser);
        enrolment.setEnrolmentDatetime(LocalDateTime.now());
        enrolment.setIsAuthor(false);
        enrolmentRepository.save(enrolment);

        enrolmentService.deleteEnrolment(savedCourse.getId(), savedUser.getId());

        Optional<Enrolment> deletedEnrolment = enrolmentRepository.findById(enrolmentId);
        assertFalse(deletedEnrolment.isPresent());
    }

    @Test
    @Transactional
    public void testIsEnrolled() {
        EnrolmentId enrolmentId = new EnrolmentId(savedCourse.getId(), savedUser.getId());
        Enrolment enrolment = new Enrolment();
        enrolment.setId(enrolmentId);
        enrolment.setCourse(savedCourse);
        enrolment.setStudent(savedUser);
        enrolment.setEnrolmentDatetime(LocalDateTime.now());
        enrolment.setIsAuthor(false);
        enrolmentRepository.save(enrolment);

        boolean isEnrolled = enrolmentService.isEnrolled(savedCourse.getId(), savedUser.getId());

        assertTrue(isEnrolled);
    }

    @Test
    @Transactional
    public void testGetAllEnrolments() {
        EnrolmentId enrolmentId1 = new EnrolmentId(savedCourse.getId(), savedUser.getId());
        Enrolment enrolment1 = new Enrolment();
        enrolment1.setId(enrolmentId1);
        enrolment1.setCourse(savedCourse);
        enrolment1.setStudent(savedUser);
        enrolment1.setEnrolmentDatetime(LocalDateTime.now());
        enrolment1.setIsAuthor(false);
        enrolmentRepository.save(enrolment1);

        Course secondCourse = new Course();
        secondCourse.setName("Second Test Course");
        secondCourse.setDescription("Second Test Course Description");
        secondCourse.setPrice(2000L);
        secondCourse.setIsProgressLimited(true);
        secondCourse.setPicture_url("http://testurl.com/picture2");
        secondCourse.setCategory(savedCourse.getCategory());
        secondCourse = courseRepository.save(secondCourse);

        EnrolmentId enrolmentId2 = new EnrolmentId(secondCourse.getId(), savedUser.getId());
        Enrolment enrolment2 = new Enrolment();
        enrolment2.setId(enrolmentId2);
        enrolment2.setCourse(secondCourse);
        enrolment2.setStudent(savedUser);
        enrolment2.setEnrolmentDatetime(LocalDateTime.now());
        enrolment2.setIsAuthor(true);
        enrolmentRepository.save(enrolment2);

        List<EnrolmentDto> enrolments = enrolmentService.getAllEnrolments();
        assertEquals(2, enrolments.size());
    }
}
