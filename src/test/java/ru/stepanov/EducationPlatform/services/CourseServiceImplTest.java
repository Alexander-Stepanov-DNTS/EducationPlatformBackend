package ru.stepanov.EducationPlatform.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.CategoryDto;
import ru.stepanov.EducationPlatform.DTO.CourseDto;
import ru.stepanov.EducationPlatform.models.Category;
import ru.stepanov.EducationPlatform.models.Course;
import ru.stepanov.EducationPlatform.models.Direction;
import ru.stepanov.EducationPlatform.repositories.CategoryRepository;
import ru.stepanov.EducationPlatform.repositories.CourseRepository;
import ru.stepanov.EducationPlatform.repositories.DirectionRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CourseServiceImplTest {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DirectionRepository directionRepository;

    private Category savedCategory;

    @BeforeEach
    public void setUp() {
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
        savedCategory = categoryRepository.save(category);
    }

    @Test
    @Transactional
    public void testGetCourseById() {
        Course course = new Course();
        course.setName("Test Course");
        course.setDescription("Test Description");
        course.setPrice(1000L);
        course.setIsProgressLimited(true);
        course.setPicture_url("http://testurl.com/picture");
        course.setCategory(savedCategory);
        course = courseRepository.save(course);

        CourseDto foundCourse = courseService.getCourseById(course.getId());
        assertNotNull(foundCourse);
        assertEquals(course.getId(), foundCourse.getId());
        assertEquals(course.getName(), foundCourse.getName());
        assertEquals(course.getDescription(), foundCourse.getDescription());
        assertEquals(course.getPrice(), foundCourse.getPrice());
        assertEquals(course.getIsProgressLimited(), foundCourse.getIsProgressLimited());
        assertEquals(course.getPicture_url(), foundCourse.getPicture_url());
        assertEquals(course.getCategory().getId(), foundCourse.getCategory().getId());
    }

    @Test
    @Transactional
    public void testGetAllCourses() {
        Course course1 = new Course();
        course1.setName("Test Course 1");
        course1.setDescription("Test Description 1");
        course1.setPrice(1000L);
        course1.setIsProgressLimited(true);
        course1.setPicture_url("http://testurl.com/picture1");
        course1.setCategory(savedCategory);
        courseRepository.save(course1);

        Course course2 = new Course();
        course2.setName("Test Course 2");
        course2.setDescription("Test Description 2");
        course2.setPrice(2000L);
        course2.setIsProgressLimited(false);
        course2.setPicture_url("http://testurl.com/picture2");
        course2.setCategory(savedCategory);
        courseRepository.save(course2);

        List<CourseDto> courses = courseService.getAllCourses();
        for (CourseDto course : courses) {
            System.out.println(course);
        }
        assertEquals(2, courses.size());
    }

    @Test
    @Transactional
    public void testCreateCourse() {
        CourseDto courseDto = new CourseDto();
        courseDto.setName("New Course");
        courseDto.setDescription("New Description");
        courseDto.setPrice(3000L);
        courseDto.setIsProgressLimited(false);
        courseDto.setPicture_url("http://testurl.com/newpicture");

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(savedCategory.getId());
        courseDto.setCategory(categoryDto);

        CourseDto createdCourse = courseService.createCourse(courseDto);
        assertNotNull(createdCourse.getId());
        assertEquals(courseDto.getName(), createdCourse.getName());
        assertEquals(courseDto.getDescription(), createdCourse.getDescription());
        assertEquals(courseDto.getPrice(), createdCourse.getPrice());
        assertEquals(courseDto.getIsProgressLimited(), createdCourse.getIsProgressLimited());
        assertEquals(courseDto.getPicture_url(), createdCourse.getPicture_url());
        assertEquals(courseDto.getCategory().getId(), createdCourse.getCategory().getId());
    }

    @Test
    @Transactional
    public void testUpdateCourse() {
        Course course = new Course();
        course.setName("Old Course");
        course.setDescription("Old Description");
        course.setPrice(4000L);
        course.setIsProgressLimited(true);
        course.setPicture_url("http://testurl.com/oldpicture");
        course.setCategory(savedCategory);
        course = courseRepository.save(course);

        CourseDto courseDto = new CourseDto();
        courseDto.setName("Updated Course");
        courseDto.setDescription("Updated Description");
        courseDto.setPrice(5000L);
        courseDto.setIsProgressLimited(false);
        courseDto.setPicture_url("http://testurl.com/updatedpicture");

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(savedCategory.getId());
        courseDto.setCategory(categoryDto);

        CourseDto updatedCourse = courseService.updateCourse(course.getId(), courseDto);
        assertNotNull(updatedCourse);
        assertEquals(courseDto.getName(), updatedCourse.getName());
        assertEquals(courseDto.getDescription(), updatedCourse.getDescription());
        assertEquals(courseDto.getPrice(), updatedCourse.getPrice());
        assertEquals(courseDto.getIsProgressLimited(), updatedCourse.getIsProgressLimited());
        assertEquals(courseDto.getPicture_url(), updatedCourse.getPicture_url());
        assertEquals(courseDto.getCategory().getId(), updatedCourse.getCategory().getId());
    }

    @Test
    @Transactional
    public void testDeleteCourse() {
        Course course = new Course();
        course.setName("To Be Deleted");
        course.setDescription("To Be Deleted");
        course.setPrice(6000L);
        course.setIsProgressLimited(true);
        course.setPicture_url("http://testurl.com/tobedeleted");
        course.setCategory(savedCategory);
        course = courseRepository.save(course);

        courseService.deleteCourse(course.getId());

        Optional<Course> deletedCourse = courseRepository.findById(course.getId());
        assertFalse(deletedCourse.isPresent());
    }
}