package ru.stepanov.EducationPlatform.services;
import ru.stepanov.EducationPlatform.DTO.CourseMaterialDto;
import java.util.List;

public interface CourseMaterialService {
    CourseMaterialDto getCourseMaterialById(Long id);
    List<CourseMaterialDto> getAllMaterialsByCourse(Long courseId);
    List<CourseMaterialDto> getAllMaterialsByUser(Long userId);
    List<CourseMaterialDto> getAllCourseMaterials();
    CourseMaterialDto createCourseMaterial(CourseMaterialDto courseMaterialDto);
    CourseMaterialDto updateCourseMaterial(Long id, CourseMaterialDto courseMaterialDto);
    void deleteCourseMaterial(Long id);
}