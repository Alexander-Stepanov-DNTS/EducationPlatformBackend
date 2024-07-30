package ru.stepanov.EducationPlatform.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.stepanov.EducationPlatform.models.EmbeddedId.UserAchievementId;
import jakarta.persistence.Entity;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_achievement")
public class UserAchievement {

    @EmbeddedId
    private UserAchievementId id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User student;

    @ManyToOne
    @MapsId("achievementId")
    @JoinColumn(name = "achievement_id", referencedColumnName = "id")
    private Achievement achievement;

    @Column(nullable = false)
    private LocalDate dateAchieved;
}