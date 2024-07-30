package ru.stepanov.EducationPlatform.models.EmbeddedId;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
public class UserAchievementId implements Serializable {

    private Long studentId;
    private Long achievementId;

    public UserAchievementId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAchievementId that = (UserAchievementId) o;
        return Objects.equals(studentId, that.studentId) &&
                Objects.equals(achievementId, that.achievementId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, achievementId);
    }
}

