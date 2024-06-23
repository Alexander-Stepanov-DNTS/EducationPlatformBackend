package ru.stepanov.EducationPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stepanov.EducationPlatform.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
