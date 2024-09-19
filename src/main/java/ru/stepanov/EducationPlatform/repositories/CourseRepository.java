package ru.stepanov.EducationPlatform.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.stepanov.EducationPlatform.models.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("SELECT c.category.name, c.id, c.name, c.description, c.rating, c.isProgressLimited, c.picture_url " +
            "FROM Course c " +
            "GROUP BY c.category.name, c.id, c.name, c.description, c.rating, c.isProgressLimited, c.picture_url")
    List<Object[]> findCoursesGroupedByCategory();

    @Query("SELECT CASE " +
            "WHEN c.rating > 4.5 THEN 'EXCELLENT' " +
            "WHEN c.rating > 3 THEN 'GOOD' " +
            "ELSE 'BAD' END as ratingGroup, " +
            "c.id, c.name, c.description, c.isProgressLimited, c.picture_url, c.rating " +
            "FROM Course c " +
            "GROUP BY ratingGroup, c.id, c.name, c.description, c.isProgressLimited, c.picture_url, c.rating")
    List<Object[]> findCoursesGroupedByRating();
}