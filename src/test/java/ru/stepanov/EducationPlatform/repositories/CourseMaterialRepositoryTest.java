package ru.stepanov.EducationPlatform.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import ru.stepanov.EducationPlatform.models.Course;
import ru.stepanov.EducationPlatform.models.CourseMaterial;
import ru.stepanov.EducationPlatform.models.Category;
import ru.stepanov.EducationPlatform.models.Direction;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CourseMaterialRepositoryTest {

    @Autowired
    private CourseMaterialRepository courseMaterialRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DirectionRepository directionRepository;

    @Test
    public void testSaveAndFindByCourseId() {
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

        CourseMaterial material = new CourseMaterial();
        material.setCourse(course);
        material.setMaterialTitle("Java Introduction");
        material.setMaterialUrl("java_intro.pdf");
        CourseMaterial savedMaterial = courseMaterialRepository.save(material);

        List<CourseMaterial> materials = courseMaterialRepository.findByCourseId(course.getId());
        assertEquals(1, materials.size());
        assertEquals("Java Introduction", materials.get(0).getMaterialTitle());
    }

    @Test
    public void testFindByCourseIdIn() {
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
        course2.setPicture_url("adv_java.png");
        course2.setCategory(category);
        course2 = courseRepository.save(course2);

        CourseMaterial material1 = new CourseMaterial();
        material1.setCourse(course1);
        material1.setMaterialTitle("Java Introduction");
        material1.setMaterialUrl("java_intro.pdf");
        courseMaterialRepository.save(material1);

        CourseMaterial material2 = new CourseMaterial();
        material2.setCourse(course2);
        material2.setMaterialTitle("Advanced Java Techniques");
        material2.setMaterialUrl("adv_java_tech.pdf");
        courseMaterialRepository.save(material2);

        List<CourseMaterial> materials = courseMaterialRepository.findByCourseIdIn(Arrays.asList(course1.getId(), course2.getId()));
        assertEquals(2, materials.size());
        assertTrue(materials.stream().anyMatch(m -> m.getMaterialTitle().equals("Java Introduction")));
        assertTrue(materials.stream().anyMatch(m -> m.getMaterialTitle().equals("Advanced Java Techniques")));
    }
}