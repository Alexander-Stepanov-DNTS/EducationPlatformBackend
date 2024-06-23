package ru.stepanov.EducationPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stepanov.EducationPlatform.models.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
