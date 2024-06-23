package ru.stepanov.EducationPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stepanov.EducationPlatform.models.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
