package ru.stepanov.EducationPlatform.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import ru.stepanov.EducationPlatform.models.*;
import ru.stepanov.EducationPlatform.models.EmbeddedId.EnrolmentId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class EnrolmentRepositoryTest {

    @Autowired
    private EnrolmentRepository enrolmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DirectionRepository directionRepository;

    @Test
    public void testSaveAndFindById() {
        Direction direction = new Direction();
        direction.setName("Science");
        direction.setDescription("Science Direction");
        direction = directionRepository.save(direction);

        Category category = new Category();
        category.setName("Physics");
        category.setDescription("Physics Category");
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

        Role role = new Role();
        role.setName("ROLE_USER");
        role = roleRepository.save(role);

        Institution institution = new Institution();
        institution.setType("Type");
        institution.setName("Institution Name");
        institution = institutionRepository.save(institution);

        User user = new User();
        user.setEmailAddress("test@example.com");
        user.setPassword("password");
        user.setSignupDate(LocalDate.now());
        user.setLogin("testUser");
        user.setRole(role);
        user.setInstitution(institution);
        user = userRepository.save(user);

        EnrolmentId enrolmentId = new EnrolmentId(course.getId(), user.getId());
        Enrolment enrolment = new Enrolment();
        enrolment.setId(enrolmentId);
        enrolment.setCourse(course);
        enrolment.setStudent(user);
        enrolment.setEnrolmentDatetime(LocalDateTime.now());
        enrolment.setIsAuthor(false);
        enrolment = enrolmentRepository.save(enrolment);

        assertNotNull(enrolment.getId());
        Optional<Enrolment> foundEnrolment = enrolmentRepository.findById(enrolment.getId());
        assertTrue(foundEnrolment.isPresent());
        assertEquals(course.getId(), foundEnrolment.get().getCourse().getId());
        assertEquals(user.getId(), foundEnrolment.get().getStudent().getId());
    }

    @Test
    public void testFindById_CourseIdAndId_StudentId() {
        Direction direction = new Direction();
        direction.setName("Science");
        direction.setDescription("Science Direction");
        direction = directionRepository.save(direction);

        Category category = new Category();
        category.setName("Physics");
        category.setDescription("Physics Category");
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

        EnrolmentId enrolmentId = new EnrolmentId(course.getId(), user.getId());
        Enrolment enrolment = new Enrolment();
        enrolment.setId(enrolmentId);
        enrolment.setCourse(course);
        enrolment.setStudent(user);
        enrolment.setEnrolmentDatetime(LocalDateTime.now());
        enrolment.setIsAuthor(false);
        enrolment = enrolmentRepository.save(enrolment);

        Optional<Enrolment> foundEnrolment = enrolmentRepository.findById_CourseIdAndId_StudentId(course.getId(), user.getId());
        assertTrue(foundEnrolment.isPresent());
        assertEquals(course.getId(), foundEnrolment.get().getCourse().getId());
        assertEquals(user.getId(), foundEnrolment.get().getStudent().getId());
    }

    @Test
    public void testFindByStudentId() {
        Direction direction = new Direction();
        direction.setName("Science");
        direction.setDescription("Science Direction");
        direction = directionRepository.save(direction);

        Category category = new Category();
        category.setName("Physics");
        category.setDescription("Physics Category");
        category.setDirection(direction);
        category = categoryRepository.save(category);

        Course course1 = new Course();
        course1.setName("Test Course 1");
        course1.setDescription("Test Description 1");
        course1.setPrice(100L);
        course1.setIsProgressLimited(true);
        course1.setPicture_url("http://example.com/pic1.jpg");
        course1.setCategory(category);
        course1 = courseRepository.save(course1);

        Course course2 = new Course();
        course2.setName("Test Course 2");
        course2.setDescription("Test Description 2");
        course2.setPrice(200L);
        course2.setIsProgressLimited(false);
        course2.setPicture_url("http://example.com/pic2.jpg");
        course2.setCategory(category);
        course2 = courseRepository.save(course2);

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

        EnrolmentId enrolmentId1 = new EnrolmentId(course1.getId(), user.getId());
        Enrolment enrolment1 = new Enrolment();
        enrolment1.setId(enrolmentId1);
        enrolment1.setCourse(course1);
        enrolment1.setStudent(user);
        enrolment1.setEnrolmentDatetime(LocalDateTime.now());
        enrolment1.setIsAuthor(false);
        enrolment1 = enrolmentRepository.save(enrolment1);

        EnrolmentId enrolmentId2 = new EnrolmentId(course2.getId(), user.getId());
        Enrolment enrolment2 = new Enrolment();
        enrolment2.setId(enrolmentId2);
        enrolment2.setCourse(course2);
        enrolment2.setStudent(user);
        enrolment2.setEnrolmentDatetime(LocalDateTime.now());
        enrolment2.setIsAuthor(false);
        enrolment2 = enrolmentRepository.save(enrolment2);

        List<Enrolment> enrolments = enrolmentRepository.findByStudentId(user.getId());
        assertEquals(2, enrolments.size());
        Course finalCourse = course1;
        assertTrue(enrolments.stream().anyMatch(e -> e.getCourse().getId().equals(finalCourse.getId())));
        Course finalCourse1 = course2;
        assertTrue(enrolments.stream().anyMatch(e -> e.getCourse().getId().equals(finalCourse1.getId())));
    }
}