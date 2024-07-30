package ru.stepanov.EducationPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.stepanov.EducationPlatform.models.UserAchievement;
import ru.stepanov.EducationPlatform.models.EmbeddedId.UserAchievementId;

import java.util.List;

@Repository
public interface UserAchievementRepository extends JpaRepository<UserAchievement, UserAchievementId> {
    List<UserAchievement> findByStudentId(Long studentId);
}

