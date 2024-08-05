package ru.stepanov.EducationPlatform.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.CategoryDto;
import ru.stepanov.EducationPlatform.DTO.DirectionDto;
import ru.stepanov.EducationPlatform.models.Category;
import ru.stepanov.EducationPlatform.models.Direction;
import ru.stepanov.EducationPlatform.repositories.CategoryRepository;
import ru.stepanov.EducationPlatform.repositories.DirectionRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CategoryServiceImplTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DirectionRepository directionRepository;

    private Direction savedDirection;

    @BeforeEach
    public void setUp() {
        categoryRepository.deleteAll();
        directionRepository.deleteAll();

        Direction direction = new Direction();
        direction.setName("Test Direction");
        direction.setDescription("Test Direction Description");
        savedDirection = directionRepository.save(direction);
    }

    @Test
    @Transactional
    public void testGetCategoryById() {
        Category category = new Category();
        category.setName("Test Category");
        category.setDescription("Test Description");
        category.setDirection(savedDirection);
        category = categoryRepository.save(category);

        CategoryDto foundCategory = categoryService.getCategoryById(category.getId());
        assertNotNull(foundCategory);
        assertEquals(category.getId(), foundCategory.getId());
        assertEquals(category.getName(), foundCategory.getName());
        assertEquals(category.getDescription(), foundCategory.getDescription());
        assertEquals(category.getDirection().getId(), foundCategory.getDirection().getId());
    }

    @Test
    @Transactional
    public void testGetAllCategories() {
        Category category1 = new Category();
        category1.setName("Test Category 1");
        category1.setDescription("Test Description 1");
        category1.setDirection(savedDirection);
        categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setName("Test Category 2");
        category2.setDescription("Test Description 2");
        category2.setDirection(savedDirection);
        categoryRepository.save(category2);

        List<CategoryDto> categories = categoryService.getAllCategories();
        for (CategoryDto category : categories) {
            System.out.println(category);
        }
        assertEquals(2, categories.size());
    }

    @Test
    @Transactional
    public void testCreateCategory() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("New Category");
        categoryDto.setDescription("New Description");
        DirectionDto directionDto = new DirectionDto();
        directionDto.setId(savedDirection.getId());
        categoryDto.setDirection(directionDto);

        CategoryDto createdCategory = categoryService.createCategory(categoryDto);
        assertNotNull(createdCategory.getId());
        assertEquals(categoryDto.getName(), createdCategory.getName());
        assertEquals(categoryDto.getDescription(), createdCategory.getDescription());
        assertEquals(categoryDto.getDirection().getId(), createdCategory.getDirection().getId());
    }

    @Test
    @Transactional
    public void testUpdateCategory() {
        Category category = new Category();
        category.setName("Old Category");
        category.setDescription("Old Description");
        category.setDirection(savedDirection);
        category = categoryRepository.save(category);

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Updated Category");
        categoryDto.setDescription("Updated Description");
        DirectionDto directionDto = new DirectionDto();
        directionDto.setId(savedDirection.getId());
        categoryDto.setDirection(directionDto);

        CategoryDto updatedCategory = categoryService.updateCategory(category.getId(), categoryDto);
        assertNotNull(updatedCategory);
        assertEquals(categoryDto.getName(), updatedCategory.getName());
        assertEquals(categoryDto.getDescription(), updatedCategory.getDescription());
        assertEquals(categoryDto.getDirection().getId(), updatedCategory.getDirection().getId());
    }

    @Test
    @Transactional
    public void testDeleteCategory() {
        Category category = new Category();
        category.setName("To Be Deleted");
        category.setDescription("To Be Deleted");
        category.setDirection(savedDirection);
        category = categoryRepository.save(category);

        categoryService.deleteCategory(category.getId());

        Optional<Category> deletedCategory = categoryRepository.findById(category.getId());
        assertFalse(deletedCategory.isPresent());
    }
}