package ru.stepanov.EducationPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stepanov.EducationPlatform.models.EmbeddedId.EnrolmentId;
import ru.stepanov.EducationPlatform.models.Enrolment;

public interface EnrolmentRepository extends JpaRepository<Enrolment, EnrolmentId> {
}
