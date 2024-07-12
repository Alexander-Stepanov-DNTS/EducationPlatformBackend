package ru.stepanov.EducationPlatform.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "site_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String emailAddress;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDate signupDate;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id",nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "institution_id", referencedColumnName = "id", nullable = false)
    private Institution institution;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

    @OneToMany(mappedBy = "student")
    private List<Enrolment> enrolments;

    @OneToMany(mappedBy = "student")
    private List<StudentLesson> studentLessons;

    @OneToMany(mappedBy = "student")
    private List<StudentQuizAttempt> studentQuizAttempts;
}
