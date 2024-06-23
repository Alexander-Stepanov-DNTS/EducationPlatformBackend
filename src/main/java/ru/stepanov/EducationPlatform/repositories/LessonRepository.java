package ru.stepanov.EducationPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stepanov.EducationPlatform.models.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
