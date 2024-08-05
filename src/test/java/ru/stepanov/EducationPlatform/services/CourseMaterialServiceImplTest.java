package ru.stepanov.EducationPlatform.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.CourseDto;
import ru.stepanov.EducationPlatform.DTO.CourseMaterialDto;
import ru.stepanov.EducationPlatform.models.Course;
import ru.stepanov.EducationPlatform.models.CourseMaterial;
import ru.stepanov.EducationPlatform.models.Direction;
import ru.stepanov.EducationPlatform.models.Category;
import ru.stepanov.EducationPlatform.repositories.CourseMaterialRepository;
import ru.stepanov.EducationPlatform.repositories.CourseRepository;
import ru.stepanov.EducationPlatform.repositories.DirectionRepository;
import ru.stepanov.EducationPlatform.repositories.CategoryRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CourseMaterialServiceImplTest {

    @Autowired
    private CourseMaterialService courseMaterialService;

    @Autowired
    private CourseMaterialRepository courseMaterialRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private DirectionRepository directionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Course savedCourse;

    @BeforeEach
    public void setUp() {
        courseMaterialRepository.deleteAll();
        courseRepository.deleteAll();
        categoryRepository.deleteAll();
        directionRepository.deleteAll();

        // Create and save a Direction
        Direction direction = new Direction();
        direction.setName("Test Direction");
        direction.setDescription("Test Direction Description");
        Direction savedDirection = directionRepository.save(direction);

        // Create and save a Category with the direction
        Category category = new Category();
        category.setName("Test Category");
        category.setDescription("Test Category Description");
        category.setDirection(savedDirection);
        Category savedCategory = categoryRepository.save(category);

        // Create and save a Course
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
    public void testCreateCourseMaterial() {
        CourseDto courseDto = new CourseDto();
        courseDto.setId(savedCourse.getId());
        courseDto.setName(savedCourse.getName());
        courseDto.setDescription(savedCourse.getDescription());
        courseDto.setPrice(savedCourse.getPrice());
        courseDto.setIsProgressLimited(savedCourse.getIsProgressLimited());
        courseDto.setPicture_url(savedCourse.getPicture_url());

        CourseMaterialDto courseMaterialDto = new CourseMaterialDto();
        courseMaterialDto.setCourse(courseDto);
        courseMaterialDto.setMaterialTitle("Test Material Title");
        courseMaterialDto.setMaterialUrl("http://testurl.com/material");

        CourseMaterialDto createdMaterial = courseMaterialService.createCourseMaterial(courseMaterialDto);
        assertNotNull(createdMaterial.getId());
        assertEquals(courseMaterialDto.getMaterialTitle(), createdMaterial.getMaterialTitle());
        assertEquals(courseMaterialDto.getMaterialUrl(), createdMaterial.getMaterialUrl());
        assertEquals(courseMaterialDto.getCourse().getId(), createdMaterial.getCourse().getId());
    }


    @Test
    @Transactional
    public void testGetCourseMaterialById() {
        CourseMaterial courseMaterial = new CourseMaterial();
        courseMaterial.setCourse(savedCourse);
        courseMaterial.setMaterialTitle("Test Material Title");
        courseMaterial.setMaterialUrl("http://testurl.com/material");
        courseMaterial = courseMaterialRepository.save(courseMaterial);

        CourseMaterialDto foundMaterial = courseMaterialService.getCourseMaterialById(courseMaterial.getId());
        assertNotNull(foundMaterial);
        assertEquals(courseMaterial.getId(), foundMaterial.getId());
        assertEquals(courseMaterial.getMaterialTitle(), foundMaterial.getMaterialTitle());
        assertEquals(courseMaterial.getMaterialUrl(), foundMaterial.getMaterialUrl());
        assertEquals(courseMaterial.getCourse().getId(), foundMaterial.getCourse().getId());
    }

    @Test
    @Transactional
    public void testGetAllMaterialsByCourse() {
        CourseMaterial courseMaterial1 = new CourseMaterial();
        courseMaterial1.setCourse(savedCourse);
        courseMaterial1.setMaterialTitle("Material Title 1");
        courseMaterial1.setMaterialUrl("http://testurl.com/material1");
        courseMaterialRepository.save(courseMaterial1);

        CourseMaterial courseMaterial2 = new CourseMaterial();
        courseMaterial2.setCourse(savedCourse);
        courseMaterial2.setMaterialTitle("Material Title 2");
        courseMaterial2.setMaterialUrl("http://testurl.com/material2");
        courseMaterialRepository.save(courseMaterial2);

        List<CourseMaterialDto> materials = courseMaterialService.getAllMaterialsByCourse(savedCourse.getId());
        assertEquals(2, materials.size());
    }

    @Test
    @Transactional
    public void testUpdateCourseMaterial() {
        CourseMaterial courseMaterial = new CourseMaterial();
        courseMaterial.setCourse(savedCourse);
        courseMaterial.setMaterialTitle("Old Material Title");
        courseMaterial.setMaterialUrl("http://testurl.com/oldmaterial");
        courseMaterial = courseMaterialRepository.save(courseMaterial);

        CourseMaterialDto courseMaterialDto = new CourseMaterialDto();
        courseMaterialDto.setMaterialTitle("Updated Material Title");
        courseMaterialDto.setMaterialUrl("http://testurl.com/updatedmaterial");

        CourseMaterialDto updatedMaterial = courseMaterialService.updateCourseMaterial(courseMaterial.getId(), courseMaterialDto);
        assertNotNull(updatedMaterial);
        assertEquals(courseMaterialDto.getMaterialTitle(), updatedMaterial.getMaterialTitle());
        assertEquals(courseMaterialDto.getMaterialUrl(), updatedMaterial.getMaterialUrl());
    }

    @Test
    @Transactional
    public void testDeleteCourseMaterial() {
        CourseMaterial courseMaterial = new CourseMaterial();
        courseMaterial.setCourse(savedCourse);
        courseMaterial.setMaterialTitle("Material Title");
        courseMaterial.setMaterialUrl("http://testurl.com/material");
        courseMaterial = courseMaterialRepository.save(courseMaterial);

        courseMaterialService.deleteCourseMaterial(courseMaterial.getId());

        Optional<CourseMaterial> deletedMaterial = courseMaterialRepository.findById(courseMaterial.getId());
        assertFalse(deletedMaterial.isPresent());
    }
}