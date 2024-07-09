package ru.stepanov.EducationPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stepanov.EducationPlatform.models.EmbeddedId.EnrolmentId;
import ru.stepanov.EducationPlatform.models.Enrolment;

import java.util.Optional;

public interface EnrolmentRepository extends JpaRepository<Enrolment, EnrolmentId> {
    Optional<Enrolment> findById_CourseIdAndId_StudentId(Long courseId, Long studentId);
}
