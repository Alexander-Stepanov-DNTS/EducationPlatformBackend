package ru.stepanov.EducationPlatform.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.stepanov.EducationPlatform.DTO.InstitutionDto;
import ru.stepanov.EducationPlatform.models.Institution;

@Mapper
public interface InstitutionMapper {
    InstitutionMapper INSTANCE = Mappers.getMapper(InstitutionMapper.class);

    InstitutionDto toDto(Institution institution);
    Institution toEntity(InstitutionDto institutionDto);
}

