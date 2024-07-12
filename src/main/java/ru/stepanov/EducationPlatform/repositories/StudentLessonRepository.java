package ru.stepanov.EducationPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stepanov.EducationPlatform.models.EmbeddedId.StudentLessonId;
import ru.stepanov.EducationPlatform.models.StudentLesson;

public interface StudentLessonRepository extends JpaRepository<StudentLesson, StudentLessonId> {
}

