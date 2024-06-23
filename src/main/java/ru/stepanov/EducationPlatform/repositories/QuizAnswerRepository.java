package ru.stepanov.EducationPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stepanov.EducationPlatform.models.QuizAnswer;

public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, Long> {
}
