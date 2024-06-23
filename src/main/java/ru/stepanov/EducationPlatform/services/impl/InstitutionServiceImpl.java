package ru.stepanov.EducationPlatform.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.InstitutionDto;
import ru.stepanov.EducationPlatform.mappers.InstitutionMapper;
import ru.stepanov.EducationPlatform.models.Institution;
import ru.stepanov.EducationPlatform.repositories.InstitutionRepository;
import ru.stepanov.EducationPlatform.services.InstitutionService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InstitutionServiceImpl implements InstitutionService {

    @Autowired
    private InstitutionRepository institutionRepository;

    @Override
    @Transactional(readOnly = true)
    public InstitutionDto getInstitutionById(Long id) {
        Optional<Institution> institution = institutionRepository.findById(id);
        return institution.map(InstitutionMapper.INSTANCE::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InstitutionDto> getAllInstitutions() {
        return institutionRepository.findAll().stream()
                .map(InstitutionMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public InstitutionDto createInstitution(InstitutionDto institutionDto) {
        Institution institution = InstitutionMapper.INSTANCE.toEntity(institutionDto);
        institution = institutionRepository.save(institution);
        return InstitutionMapper.INSTANCE.toDto(institution);
    }

    @Override
    @Transactional
    public InstitutionDto updateInstitution(Long id, InstitutionDto institutionDto) {
        Optional<Institution> existingInstitution = institutionRepository.findById(id);
        if (existingInstitution.isPresent()) {
            Institution institution = existingInstitution.get();
            institution.setName(institutionDto.getName());
            institution.setType(institutionDto.getType());
            institution = institutionRepository.save(institution);
            return InstitutionMapper.INSTANCE.toDto(institution);
        } else {
            return null; // или выбросить исключение
        }
    }

    @Override
    @Transactional
    public void deleteInstitution(Long id) {
        institutionRepository.deleteById(id);
    }
}
