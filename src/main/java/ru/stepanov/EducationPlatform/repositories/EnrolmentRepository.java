package ru.stepanov.EducationPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.stepanov.EducationPlatform.models.EmbeddedId.EnrolmentId;
import ru.stepanov.EducationPlatform.models.Enrolment;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrolmentRepository extends JpaRepository<Enrolment, EnrolmentId> {
    Optional<Enrolment> findById_CourseIdAndId_StudentId(Long courseId, Long studentId);
    List<Enrolment> findByStudentId(Long userId);
}