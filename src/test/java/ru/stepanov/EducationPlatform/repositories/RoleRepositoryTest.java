package ru.stepanov.EducationPlatform.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import ru.stepanov.EducationPlatform.models.Role;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testSaveAndFindById() {
        Role role = new Role();
        role.setName("ROLE_USER");
        Role savedRole = roleRepository.save(role);

        assertNotNull(savedRole.getId());
        Optional<Role> foundRole = roleRepository.findById(savedRole.getId());
        assertTrue(foundRole.isPresent());
        assertEquals("ROLE_USER", foundRole.get().getName());
    }

    @Test
    public void testFindByName() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        roleRepository.save(role);

        Optional<Role> foundRole = roleRepository.findByName("ROLE_ADMIN");
        assertTrue(foundRole.isPresent());
        assertEquals("ROLE_ADMIN", foundRole.get().getName());
    }
}