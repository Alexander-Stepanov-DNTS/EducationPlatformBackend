package ru.stepanov.EducationPlatform.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import ru.stepanov.EducationPlatform.models.Institution;
import ru.stepanov.EducationPlatform.models.Role;
import ru.stepanov.EducationPlatform.models.User;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Test
    public void testSaveAndFindByEmailAddress() {
        Role role = new Role();
        role.setName("ROLE_USER");
        role = roleRepository.save(role);

        Institution institution = new Institution();
        institution.setType("University");
        institution.setName("Test University");
        institution = institutionRepository.save(institution);

        User user = new User();
        user.setEmailAddress("test@example.com");
        user.setPassword("password");
        user.setSignupDate(LocalDate.now());
        user.setLogin("testUser");
        user.setRole(role);
        user.setInstitution(institution);
        user = userRepository.save(user);

        Optional<User> foundUserOptional = userRepository.findByEmailAddress("test@example.com");
        assertTrue(foundUserOptional.isPresent());
        User foundUser = foundUserOptional.get();
        assertEquals("test@example.com", foundUser.getEmailAddress());
    }

    @Test
    public void testSaveAndFindByLogin() {
        Role role = new Role();
        role.setName("ROLE_USER");
        role = roleRepository.save(role);

        Institution institution = new Institution();
        institution.setType("University");
        institution.setName("Test University");
        institution = institutionRepository.save(institution);

        User user = new User();
        user.setEmailAddress("test2@example.com");
        user.setPassword("password2");
        user.setSignupDate(LocalDate.now());
        user.setLogin("testUser2");
        user.setRole(role);
        user.setInstitution(institution);
        user = userRepository.save(user);

        Optional<User> foundUserOptional = userRepository.findByLogin("testUser2");
        assertTrue(foundUserOptional.isPresent());
        User foundUser = foundUserOptional.get();
        assertEquals("testUser2", foundUser.getLogin());
    }
}