package ru.stepanov.EducationPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.stepanov.EducationPlatform.models.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
