package ru.stepanov.EducationPlatform.services;

import ru.stepanov.EducationPlatform.DTO.InstitutionDto;

import java.util.List;

public interface InstitutionService {
    InstitutionDto getInstitutionById(Long id);
    List<InstitutionDto> getAllInstitutions();
    InstitutionDto createInstitution(InstitutionDto institutionDto);
    InstitutionDto updateInstitution(Long id, InstitutionDto institutionDto);
    void deleteInstitution(Long id);
}

