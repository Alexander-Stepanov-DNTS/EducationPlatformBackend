package ru.stepanov.EducationPlatform.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.InstitutionDto;
import ru.stepanov.EducationPlatform.DTO.RoleDto;
import ru.stepanov.EducationPlatform.DTO.UserDto;
import ru.stepanov.EducationPlatform.DTO.CourseDto;
import ru.stepanov.EducationPlatform.models.Category;
import ru.stepanov.EducationPlatform.models.Course;
import ru.stepanov.EducationPlatform.models.Direction;
import ru.stepanov.EducationPlatform.models.EmbeddedId.EnrolmentId;
import ru.stepanov.EducationPlatform.models.Enrolment;
import ru.stepanov.EducationPlatform.models.Institution;
import ru.stepanov.EducationPlatform.models.Role;
import ru.stepanov.EducationPlatform.models.User;
import ru.stepanov.EducationPlatform.repositories.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private EnrolmentRepository enrolmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DirectionRepository directionRepository;

    private Role savedRole;
    private Institution savedInstitution;
    private Course savedCourse;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        institutionRepository.deleteAll();
        enrolmentRepository.deleteAll();
        courseRepository.deleteAll();
        categoryRepository.deleteAll();
        directionRepository.deleteAll();  // Assuming you have a DirectionRepository

        Role role = new Role();
        role.setName("Test Role");
        savedRole = roleRepository.save(role);

        Institution institution = new Institution();
        institution.setName("Test Institution");
        institution.setType("University");
        savedInstitution = institutionRepository.save(institution);

        Direction direction = new Direction();
        direction.setName("Test Direction");
        direction.setDescription("Test Direction Description");
        Direction savedDirection = directionRepository.save(direction);  // Save the direction

        Category category = new Category();
        category.setName("Test Category");
        category.setDescription("Test Category Description");
        category.setDirection(savedDirection);  // Set the direction
        Category savedCategory = categoryRepository.save(category);

        Course course = new Course();
        course.setName("Test Course");
        course.setDescription("Test Course Description");
        course.setPrice(1000L);
        course.setIsProgressLimited(true);
        course.setPicture_url("http://testurl.com/picture");
        course.setCategory(savedCategory);  // Set the category
        savedCourse = courseRepository.save(course);
    }

    @Test
    @Transactional
    public void testGetUserById() {
        User user = new User();
        user.setEmailAddress("testuser@example.com");
        user.setPassword("password");
        user.setSignupDate(LocalDate.now());
        user.setLogin("testuser");
        user.setRole(savedRole);
        user.setInstitution(savedInstitution);
        user = userRepository.save(user);

        UserDto foundUser = userService.getUserById(user.getId());
        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
        assertEquals(user.getEmailAddress(), foundUser.getEmailAddress());
        assertEquals(user.getPassword(), foundUser.getPassword());
        assertEquals(user.getSignupDate(), foundUser.getSignupDate());
        assertEquals(user.getLogin(), foundUser.getLogin());
        assertEquals(user.getRole().getId(), foundUser.getRole().getId());
        assertEquals(user.getInstitution().getId(), foundUser.getInstitution().getId());
    }

    @Test
    @Transactional
    public void testGetAllUsers() {
        User user1 = new User();
        user1.setEmailAddress("user1@example.com");
        user1.setPassword("password1");
        user1.setSignupDate(LocalDate.now());
        user1.setLogin("user1");
        user1.setRole(savedRole);
        user1.setInstitution(savedInstitution);
        userRepository.save(user1);

        User user2 = new User();
        user2.setEmailAddress("user2@example.com");
        user2.setPassword("password2");
        user2.setSignupDate(LocalDate.now());
        user2.setLogin("user2");
        user2.setRole(savedRole);
        user2.setInstitution(savedInstitution);
        userRepository.save(user2);

        List<UserDto> users = userService.getAllUsers();
        for (UserDto user : users) {
            System.out.println(user);
        }
        assertEquals(2, users.size());
    }

    @Test
    @Transactional
    public void testCreateUser() {
        UserDto userDto = new UserDto();
        userDto.setEmailAddress("newuser@example.com");
        userDto.setPassword("newpassword");
        userDto.setSignupDate(LocalDate.now());
        userDto.setLogin("newuser");
        RoleDto roleDto = new RoleDto();
        roleDto.setId(savedRole.getId());
        userDto.setRole(roleDto);
        InstitutionDto institutionDto = new InstitutionDto();
        institutionDto.setId(savedInstitution.getId());
        userDto.setInstitution(institutionDto);

        UserDto createdUser = userService.createUser(userDto);
        assertNotNull(createdUser.getId());
        assertEquals(userDto.getEmailAddress(), createdUser.getEmailAddress());
        assertEquals(userDto.getPassword(), createdUser.getPassword());
        assertEquals(userDto.getSignupDate(), createdUser.getSignupDate());
        assertEquals(userDto.getLogin(), createdUser.getLogin());
        assertEquals(userDto.getRole().getId(), createdUser.getRole().getId());
        assertEquals(userDto.getInstitution().getId(), createdUser.getInstitution().getId());
    }

    @Test
    @Transactional
    public void testUpdateUser() {
        User user = new User();
        user.setEmailAddress("olduser@example.com");
        user.setPassword("oldpassword");
        user.setSignupDate(LocalDate.now());
        user.setLogin("olduser");
        user.setRole(savedRole);
        user.setInstitution(savedInstitution);
        user = userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setEmailAddress("updateduser@example.com");
        userDto.setPassword("updatedpassword");
        userDto.setSignupDate(LocalDate.now());
        userDto.setLogin("updateduser");
        RoleDto roleDto = new RoleDto();
        roleDto.setId(savedRole.getId());
        userDto.setRole(roleDto);
        InstitutionDto institutionDto = new InstitutionDto();
        institutionDto.setId(savedInstitution.getId());
        userDto.setInstitution(institutionDto);

        UserDto updatedUser = userService.updateUser(user.getId(), userDto);
        assertNotNull(updatedUser);
        assertEquals(userDto.getEmailAddress(), updatedUser.getEmailAddress());
        assertEquals(userDto.getPassword(), updatedUser.getPassword());
        assertEquals(userDto.getSignupDate(), updatedUser.getSignupDate());
        assertEquals(userDto.getLogin(), updatedUser.getLogin());
        assertEquals(userDto.getRole().getId(), updatedUser.getRole().getId());
        assertEquals(userDto.getInstitution().getId(), updatedUser.getInstitution().getId());
    }

    @Test
    @Transactional
    public void testDeleteUser() {
        User user = new User();
        user.setEmailAddress("tobedeleted@example.com");
        user.setPassword("password");
        user.setSignupDate(LocalDate.now());
        user.setLogin("tobedeleted");
        user.setRole(savedRole);
        user.setInstitution(savedInstitution);
        user = userRepository.save(user);

        userService.deleteUser(user.getId());

        Optional<User> deletedUser = userRepository.findById(user.getId());
        assertFalse(deletedUser.isPresent());
    }

    @Test
    @Transactional
    public void testGetCoursesByUserId() {
        User user = new User();
        user.setEmailAddress("userwithcourses@example.com");
        user.setPassword("password");
        user.setSignupDate(LocalDate.now());
        user.setLogin("userwithcourses");
        user.setRole(savedRole);
        user.setInstitution(savedInstitution);
        user = userRepository.save(user);

        Enrolment enrolment = new Enrolment();
        EnrolmentId enrolmentId = new EnrolmentId(user.getId(), savedCourse.getId());
        enrolment.setId(enrolmentId);
        enrolment.setStudent(user);
        enrolment.setCourse(savedCourse);
        enrolment.setEnrolmentDatetime(LocalDate.now().atStartOfDay());
        enrolment.setIsAuthor(false);
        enrolmentRepository.save(enrolment);

        List<CourseDto> courses = userService.getCoursesByUserId(user.getId());
        assertEquals(1, courses.size());
        assertEquals(savedCourse.getId(), courses.get(0).getId());
    }
}