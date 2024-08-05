package ru.stepanov.EducationPlatform.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.InstitutionDto;
import ru.stepanov.EducationPlatform.models.Institution;
import ru.stepanov.EducationPlatform.repositories.InstitutionRepository;
import ru.stepanov.EducationPlatform.services.impl.InstitutionServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class InstitutionServiceImplTest {

    @Autowired
    private InstitutionServiceImpl institutionService;

    @Autowired
    private InstitutionRepository institutionRepository;

    private Institution savedInstitution;

    @BeforeEach
    public void setUp() {
        institutionRepository.deleteAll();

        // Create and save an Institution
        Institution institution = new Institution();
        institution.setName("Test Institution");
        institution.setType("University");
        savedInstitution = institutionRepository.save(institution);
    }

    @Test
    @Transactional
    public void testGetInstitutionById() {
        InstitutionDto foundInstitution = institutionService.getInstitutionById(savedInstitution.getId());
        assertNotNull(foundInstitution);
        assertEquals(savedInstitution.getId(), foundInstitution.getId());
        assertEquals(savedInstitution.getName(), foundInstitution.getName());
        assertEquals(savedInstitution.getType(), foundInstitution.getType());
    }

    @Test
    @Transactional
    public void testGetAllInstitutions() {
        List<InstitutionDto> institutions = institutionService.getAllInstitutions();
        assertFalse(institutions.isEmpty());
        assertEquals(1, institutions.size());
        assertEquals(savedInstitution.getId(), institutions.get(0).getId());
        assertEquals(savedInstitution.getName(), institutions.get(0).getName());
        assertEquals(savedInstitution.getType(), institutions.get(0).getType());
    }

    @Test
    @Transactional
    public void testCreateInstitution() {
        InstitutionDto institutionDto = new InstitutionDto();
        institutionDto.setName("New Institution");
        institutionDto.setType("College");

        InstitutionDto createdInstitution = institutionService.createInstitution(institutionDto);
        assertNotNull(createdInstitution.getId());
        assertEquals(institutionDto.getName(), createdInstitution.getName());
        assertEquals(institutionDto.getType(), createdInstitution.getType());
    }

    @Test
    @Transactional
    public void testUpdateInstitution() {
        InstitutionDto institutionDto = new InstitutionDto();
        institutionDto.setName("Updated Institution");
        institutionDto.setType("School");

        InstitutionDto updatedInstitution = institutionService.updateInstitution(savedInstitution.getId(), institutionDto);
        assertNotNull(updatedInstitution);
        assertEquals(savedInstitution.getId(), updatedInstitution.getId());
        assertEquals(institutionDto.getName(), updatedInstitution.getName());
        assertEquals(institutionDto.getType(), updatedInstitution.getType());
    }

    @Test
    @Transactional
    public void testDeleteInstitution() {
        institutionService.deleteInstitution(savedInstitution.getId());
        Optional<Institution> deletedInstitution = institutionRepository.findById(savedInstitution.getId());
        assertFalse(deletedInstitution.isPresent());
    }
}