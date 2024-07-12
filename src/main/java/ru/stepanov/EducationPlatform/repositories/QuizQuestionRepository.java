package ru.stepanov.EducationPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stepanov.EducationPlatform.models.QuizQuestion;

import java.util.List;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {
    List<QuizQuestion> findByQuizId(Long quizId);

}
