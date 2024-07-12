package ru.stepanov.EducationPlatform.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.CategoryDto;
import ru.stepanov.EducationPlatform.mappers.CategoryMapper;
import ru.stepanov.EducationPlatform.mappers.DirectionMapper;
import ru.stepanov.EducationPlatform.models.Category;
import ru.stepanov.EducationPlatform.repositories.CategoryRepository;
import ru.stepanov.EducationPlatform.services.CategoryService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(CategoryMapper.INSTANCE::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = CategoryMapper.INSTANCE.toEntity(categoryDto);
        category = categoryRepository.save(category);
        return CategoryMapper.INSTANCE.toDto(category);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Optional<Category> existingCategory = categoryRepository.findById(id);
        if (existingCategory.isPresent()) {
            Category category = existingCategory.get();
            category.setName(categoryDto.getName());
            category.setDescription(categoryDto.getDescription());
            category.setDirection(DirectionMapper.INSTANCE.toEntity(categoryDto.getDirection()));
            category = categoryRepository.save(category);
            return CategoryMapper.INSTANCE.toDto(category);
        } else {
            return null; // или выбросить исключение
        }
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
