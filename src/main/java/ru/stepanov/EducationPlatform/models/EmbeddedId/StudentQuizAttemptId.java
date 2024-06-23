package ru.stepanov.EducationPlatform.models.EmbeddedId;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
public class StudentQuizAttemptId implements Serializable {
    private Long studentId;
    private Long quizId;
    private LocalDateTime attemptDatetime;

    public StudentQuizAttemptId() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentQuizAttemptId that = (StudentQuizAttemptId) o;
        return Objects.equals(studentId, that.studentId) &&
                Objects.equals(quizId, that.quizId) &&
                Objects.equals(attemptDatetime, that.attemptDatetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, quizId, attemptDatetime);
    }
}
