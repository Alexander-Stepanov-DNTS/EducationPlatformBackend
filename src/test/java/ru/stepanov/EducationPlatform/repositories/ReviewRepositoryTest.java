package ru.stepanov.EducationPlatform.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import ru.stepanov.EducationPlatform.models.Category;
import ru.stepanov.EducationPlatform.models.Course;
import ru.stepanov.EducationPlatform.models.Direction;
import ru.stepanov.EducationPlatform.models.Institution;
import ru.stepanov.EducationPlatform.models.Review;
import ru.stepanov.EducationPlatform.models.Role;
import ru.stepanov.EducationPlatform.models.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private DirectionRepository directionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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

        Role role = new Role();
        role.setName("ROLE_USER");
        role = roleRepository.save(role);

        Institution institution = new Institution();
        institution.setName("Test Institution");
        institution.setType("University");
        institution = institutionRepository.save(institution);

        User user = new User();
        user.setEmailAddress("test@example.com");
        user.setPassword("password");
        user.setSignupDate(LocalDate.now());
        user.setLogin("testUser");
        user.setRole(role);
        user.setInstitution(institution);
        user = userRepository.save(user);

        Review review = new Review();
        review.setUser(user);
        review.setCourse(course);
        review.setCreatedDate(LocalDateTime.now());
        review.setScore(5);
        review.setReviewText("Great course!");
        Review savedReview = reviewRepository.save(review);

        assertNotNull(savedReview.getId());
        List<Review> foundReviews = reviewRepository.findByCourseId(course.getId());
        assertEquals(1, foundReviews.size());
        assertEquals("Great course!", foundReviews.get(0).getReviewText());
    }

    @Test
    public void testFindByCourseId() {
        Direction direction = new Direction();
        direction.setName("IT");
        direction.setDescription("Information Technology");
        direction = directionRepository.save(direction);

        Category category = new Category();
        category.setName("Programming");
        category.setDescription("All about programming");
        category.setDirection(direction);
        category = categoryRepository.save(category);

        Course course1 = new Course();
        course1.setName("Java Basics");
        course1.setDescription("Learn Java from scratch");
        course1.setPrice(1000L);
        course1.setIsProgressLimited(false);
        course1.setPicture_url("java.png");
        course1.setCategory(category);
        course1 = courseRepository.save(course1);

        Course course2 = new Course();
        course2.setName("Advanced Java");
        course2.setDescription("Deep dive into Java");
        course2.setPrice(2000L);
        course2.setIsProgressLimited(true);
        course2.setPicture_url("advanced_java.png");
        course2.setCategory(category);
        course2 = courseRepository.save(course2);

        Role role = new Role();
        role.setName("ROLE_USER");
        role = roleRepository.save(role);

        Institution institution = new Institution();
        institution.setName("Test Institution");
        institution.setType("University");
        institution = institutionRepository.save(institution);

        User user = new User();
        user.setEmailAddress("test@example.com");
        user.setPassword("password");
        user.setSignupDate(LocalDate.now());
        user.setLogin("testUser");
        user.setRole(role);
        user.setInstitution(institution);
        user = userRepository.save(user);

        Review review1 = new Review();
        review1.setUser(user);
        review1.setCourse(course1);
        review1.setCreatedDate(LocalDateTime.now());
        review1.setScore(5);
        review1.setReviewText("Great course!");
        review1 = reviewRepository.save(review1);

        Review review2 = new Review();
        review2.setUser(user);
        review2.setCourse(course2);
        review2.setCreatedDate(LocalDateTime.now());
        review2.setScore(4);
        review2.setReviewText("Good course!");
        review2 = reviewRepository.save(review2);

        List<Review> reviewsCourse1 = reviewRepository.findByCourseId(course1.getId());
        assertEquals(1, reviewsCourse1.size());
        assertEquals("Great course!", reviewsCourse1.get(0).getReviewText());

        List<Review> reviewsCourse2 = reviewRepository.findByCourseId(course2.getId());
        assertEquals(1, reviewsCourse2.size());
        assertEquals("Good course!", reviewsCourse2.get(0).getReviewText());
    }
}