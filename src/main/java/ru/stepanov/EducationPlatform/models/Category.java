package ru.stepanov.EducationPlatform.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    public Category(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Category() {
    }

    @ManyToOne
    @JoinColumn(name = "direction_id", referencedColumnName = "id") //,nullable = false
    private Direction direction;

    @OneToMany(mappedBy = "category")
    private List<Course> courseList;
}
