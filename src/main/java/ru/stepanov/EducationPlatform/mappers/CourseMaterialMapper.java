package ru.stepanov.EducationPlatform.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.stepanov.EducationPlatform.DTO.CourseMaterialDto;
import ru.stepanov.EducationPlatform.models.CourseMaterial;

@Mapper
public interface CourseMaterialMapper {
    CourseMaterialMapper INSTANCE = Mappers.getMapper(CourseMaterialMapper.class);

    CourseMaterialDto toDto(CourseMaterial courseMaterial);
    CourseMaterial toEntity(CourseMaterialDto courseMaterialDto);
}
