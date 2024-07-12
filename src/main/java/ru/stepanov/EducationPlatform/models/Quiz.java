package ru.stepanov.EducationPlatform.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private Boolean isActive;

    private Date dueDate;

    private Date reminderDate;

    @Column(nullable = false)
    private Integer courseOrder;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id",nullable = false)
    private Course course;

    @OneToMany(mappedBy = "quiz")
    private List<StudentQuizAttempt> studentQuizAttempts;

    @OneToMany(mappedBy = "quiz")
    private List<QuizQuestion> quizQuestions;
}
