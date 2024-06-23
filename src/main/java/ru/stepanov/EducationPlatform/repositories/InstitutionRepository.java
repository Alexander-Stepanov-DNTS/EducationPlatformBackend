package ru.stepanov.EducationPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stepanov.EducationPlatform.models.Institution;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {
}

