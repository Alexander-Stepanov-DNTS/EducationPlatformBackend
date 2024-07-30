package ru.stepanov.EducationPlatform.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import ru.stepanov.EducationPlatform.models.Institution;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class InstitutionRepositoryTest {

    @Autowired
    private InstitutionRepository institutionRepository;

    @Test
    public void testSaveAndFindById() {
        Institution institution = new Institution();
        institution.setName("Test University");
        institution.setType("University");
        Institution savedInstitution = institutionRepository.save(institution);

        assertNotNull(savedInstitution.getId());
        Optional<Institution> foundInstitution = institutionRepository.findById(savedInstitution.getId());
        assertTrue(foundInstitution.isPresent());
        assertEquals("Test University", foundInstitution.get().getName());
        assertEquals("University", foundInstitution.get().getType());
    }

    @Test
    public void testUpdateInstitution() {
        Institution institution = new Institution();
        institution.setName("Test University");
        institution.setType("University");
        Institution savedInstitution = institutionRepository.save(institution);

        savedInstitution.setName("Updated University");
        Institution updatedInstitution = institutionRepository.save(savedInstitution);

        Optional<Institution> foundInstitution = institutionRepository.findById(updatedInstitution.getId());
        assertTrue(foundInstitution.isPresent());
        assertEquals("Updated University", foundInstitution.get().getName());
    }

    @Test
    public void testDeleteInstitution() {
        Institution institution = new Institution();
        institution.setName("Test University");
        institution.setType("University");
        Institution savedInstitution = institutionRepository.save(institution);

        institutionRepository.delete(savedInstitution);

        Optional<Institution> foundInstitution = institutionRepository.findById(savedInstitution.getId());
        assertFalse(foundInstitution.isPresent());
    }
}

