package ru.stepanov.EducationPlatform.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.stepanov.EducationPlatform.models.EmbeddedId.EnrolmentId;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "enrolment")
public class Enrolment {
    @EmbeddedId
    private EnrolmentId id;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private User student;

    @Column(nullable = false)
    private LocalDateTime enrolmentDatetime;

    @Column()
    private LocalDateTime completedDatetime;

    @Column(nullable = false)
    private Boolean isAuthor;
}