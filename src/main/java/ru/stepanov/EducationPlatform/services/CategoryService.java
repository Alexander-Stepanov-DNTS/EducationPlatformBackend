package ru.stepanov.EducationPlatform.services;

import ru.stepanov.EducationPlatform.DTO.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto getCategoryById(Long id);
    List<CategoryDto> getAllCategories();
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(Long id, CategoryDto categoryDto);
    void deleteCategory(Long id);
}
