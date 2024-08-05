package ru.stepanov.EducationPlatform.models.EmbeddedId;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
public class StudentLessonId implements Serializable {
    private Long studentId;
    private Long lessonId;

    public StudentLessonId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentLessonId that = (StudentLessonId) o;
        return Objects.equals(studentId, that.studentId) &&
                Objects.equals(lessonId, that.lessonId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, lessonId);
    }
}
