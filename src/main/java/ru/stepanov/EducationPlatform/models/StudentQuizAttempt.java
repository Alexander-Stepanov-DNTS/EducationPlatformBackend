package ru.stepanov.EducationPlatform.models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.stepanov.EducationPlatform.models.EmbeddedId.StudentQuizAttemptId;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student_quiz_attempt")
public class StudentQuizAttempt {
    @EmbeddedId
    private StudentQuizAttemptId id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private User student;

    @ManyToOne
    @MapsId("quizId")
    @JoinColumn(name = "quiz_id", referencedColumnName = "id")
    private Quiz quiz;

    @Column(nullable = false, insertable = false, updatable = false)
    private LocalDateTime attemptDatetime;

    @Column(nullable = false)
    private Integer scoreAchieved;
}