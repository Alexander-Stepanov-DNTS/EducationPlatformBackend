package ru.stepanov.EducationPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.stepanov.EducationPlatform.models.EmbeddedId.StudentQuizAttemptId;
import ru.stepanov.EducationPlatform.models.StudentQuizAttempt;

@Repository
public interface StudentQuizAttemptRepository extends JpaRepository<StudentQuizAttempt, StudentQuizAttemptId> {
}