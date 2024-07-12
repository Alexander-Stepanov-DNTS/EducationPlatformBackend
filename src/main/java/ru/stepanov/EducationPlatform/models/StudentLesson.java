package ru.stepanov.EducationPlatform.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.stepanov.EducationPlatform.models.EmbeddedId.StudentLessonId;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student_lesson")
public class StudentLesson {
    @EmbeddedId
    private StudentLessonId id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private User student;

    @ManyToOne
    @MapsId("lessonId")
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    private Lesson lesson;

    @Column()
    private LocalDateTime completedDatetime;
}
