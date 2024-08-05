package ru.stepanov.EducationPlatform.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.CourseDto;
import ru.stepanov.EducationPlatform.DTO.ReviewDto;
import ru.stepanov.EducationPlatform.DTO.UserDto;
import ru.stepanov.EducationPlatform.models.Category;
import ru.stepanov.EducationPlatform.models.Course;
import ru.stepanov.EducationPlatform.models.Direction;
import ru.stepanov.EducationPlatform.models.Institution;
import ru.stepanov.EducationPlatform.models.Review;
import ru.stepanov.EducationPlatform.models.Role;
import ru.stepanov.EducationPlatform.models.User;
import ru.stepanov.EducationPlatform.repositories.CategoryRepository;
import ru.stepanov.EducationPlatform.repositories.CourseRepository;
import ru.stepanov.EducationPlatform.repositories.DirectionRepository;
import ru.stepanov.EducationPlatform.repositories.InstitutionRepository;
import ru.stepanov.EducationPlatform.repositories.ReviewRepository;
import ru.stepanov.EducationPlatform.repositories.RoleRepository;
import ru.stepanov.EducationPlatform.repositories.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ReviewServiceImplTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

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
    private Course savedCourse;
    private Review savedReview;

    @BeforeEach
    public void setUp() {
        reviewRepository.deleteAll();
        userRepository.deleteAll();
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
        savedCourse = courseRepository.save(course);

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

        Review review = new Review();
        review.setUser(savedUser);
        review.setCourse(savedCourse);
        review.setCreatedDate(LocalDateTime.now());
        review.setScore(5);
        review.setReviewText("Great course!");
        savedReview = reviewRepository.save(review);
    }

    @Test
    @Transactional
    public void testGetReviewById() {
        ReviewDto foundReview = reviewService.getReviewById(savedReview.getId());
        assertNotNull(foundReview);
        assertEquals(savedReview.getId(), foundReview.getId());
        assertEquals(savedReview.getScore(), foundReview.getScore());
        assertEquals(savedReview.getReviewText(), foundReview.getReviewText());
        assertEquals(savedReview.getUser().getId(), foundReview.getUser().getId());
        assertEquals(savedReview.getCourse().getId(), foundReview.getCourse().getId());
    }

    @Test
    @Transactional
    public void testGetAllReviewsByCourse() {
        List<ReviewDto> reviews = reviewService.getAllReviewsByCourse(savedCourse.getId());
        assertFalse(reviews.isEmpty());
        assertEquals(1, reviews.size());
        assertEquals(savedReview.getId(), reviews.get(0).getId());
    }

    @Test
    @Transactional
    public void testGetAllReviews() {
        List<ReviewDto> reviews = reviewService.getAllReviews();
        assertFalse(reviews.isEmpty());
        assertEquals(1, reviews.size());
    }

    @Test
    @Transactional
    public void testCreateReview() {
        ReviewDto reviewDto = new ReviewDto();
        UserDto userDto = new UserDto();
        userDto.setId(savedUser.getId());
        reviewDto.setUser(userDto);

        CourseDto courseDto = new CourseDto();
        courseDto.setId(savedCourse.getId());
        reviewDto.setCourse(courseDto);

        reviewDto.setScore(4);
        reviewDto.setReviewText("Good course!");

        ReviewDto createdReview = reviewService.createReview(reviewDto);
        assertNotNull(createdReview.getId());
        assertEquals(reviewDto.getScore(), createdReview.getScore());
        assertEquals(reviewDto.getReviewText(), createdReview.getReviewText());
        assertEquals(reviewDto.getUser().getId(), createdReview.getUser().getId());
        assertEquals(reviewDto.getCourse().getId(), createdReview.getCourse().getId());
    }

    @Test
    @Transactional
    public void testUpdateReview() {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setScore(3);
        reviewDto.setReviewText("Average course");

        UserDto userDto = new UserDto();
        userDto.setId(savedUser.getId());
        reviewDto.setUser(userDto);

        CourseDto courseDto = new CourseDto();
        courseDto.setId(savedCourse.getId());
        reviewDto.setCourse(courseDto);

        ReviewDto updatedReview = reviewService.updateReview(savedReview.getId(), reviewDto);
        assertNotNull(updatedReview);
        assertEquals(savedReview.getId(), updatedReview.getId());
        assertEquals(reviewDto.getScore(), updatedReview.getScore());
        assertEquals(reviewDto.getReviewText(), updatedReview.getReviewText());
        assertEquals(reviewDto.getUser().getId(), updatedReview.getUser().getId());
        assertEquals(reviewDto.getCourse().getId(), updatedReview.getCourse().getId());
    }

    @Test
    @Transactional
    public void testDeleteReview() {
        reviewService.deleteReview(savedReview.getId());
        Optional<Review> deletedReview = reviewRepository.findById(savedReview.getId());
        assertFalse(deletedReview.isPresent());
    }
}