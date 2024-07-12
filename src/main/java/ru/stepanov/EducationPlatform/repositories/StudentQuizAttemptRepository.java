package ru.stepanov.EducationPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stepanov.EducationPlatform.models.EmbeddedId.StudentQuizAttemptId;
import ru.stepanov.EducationPlatform.models.StudentQuizAttempt;

public interface StudentQuizAttemptRepository extends JpaRepository<StudentQuizAttempt, StudentQuizAttemptId> {
}
