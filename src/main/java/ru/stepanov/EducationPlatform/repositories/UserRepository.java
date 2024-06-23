package ru.stepanov.EducationPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stepanov.EducationPlatform.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
