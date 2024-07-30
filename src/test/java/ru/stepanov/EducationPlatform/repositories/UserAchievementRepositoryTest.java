package ru.stepanov.EducationPlatform.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import ru.stepanov.EducationPlatform.models.*;
import ru.stepanov.EducationPlatform.models.EmbeddedId.UserAchievementId;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserAchievementRepositoryTest {

    @Autowired
    private UserAchievementRepository userAchievementRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AchievementRepository achievementRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testSaveAndFindById() {
        Institution institution = new Institution();
        institution.setName("Test Institution");
        institution.setType("University");
        institution = institutionRepository.save(institution);

        Role role = new Role();
        role.setName("ROLE_USER");
        role = roleRepository.save(role);

        User user = new User();
        user.setEmailAddress("test@example.com");
        user.setPassword("password");
        user.setSignupDate(LocalDate.now());
        user.setLogin("testUser");
        user.setRole(role);
        user.setInstitution(institution);
        user = userRepository.save(user);

        Achievement achievement = new Achievement();
        achievement.setTitle("First Achievement");
        achievement.setDescription("Description for first achievement");
        achievement = achievementRepository.save(achievement);

        UserAchievementId userAchievementId = new UserAchievementId(user.getId(), achievement.getId());
        UserAchievement userAchievement = new UserAchievement();
        userAchievement.setId(userAchievementId);
        userAchievement.setStudent(user);
        userAchievement.setAchievement(achievement);
        userAchievement.setDateAchieved(LocalDate.now());
        userAchievement = userAchievementRepository.save(userAchievement);

        assertNotNull(userAchievement.getId());
        Optional<UserAchievement> foundUserAchievement = userAchievementRepository.findById(userAchievement.getId());
        assertTrue(foundUserAchievement.isPresent());
        assertEquals(user.getId(), foundUserAchievement.get().getStudent().getId());
        assertEquals(achievement.getId(), foundUserAchievement.get().getAchievement().getId());
        assertEquals(LocalDate.now(), foundUserAchievement.get().getDateAchieved());
    }

    @Test
    public void testFindByStudentId() {
        Institution institution = new Institution();
        institution.setName("Test Institution");
        institution.setType("University");
        institution = institutionRepository.save(institution);

        Role role = new Role();
        role.setName("ROLE_USER");
        role = roleRepository.save(role);

        User user = new User();
        user.setEmailAddress("test@example.com");
        user.setPassword("password");
        user.setSignupDate(LocalDate.now());
        user.setLogin("testUser");
        user.setRole(role);
        user.setInstitution(institution);
        user = userRepository.save(user);

        Achievement achievement1 = new Achievement();
        achievement1.setTitle("Achievement One");
        achievement1.setDescription("Description for achievement one");
        achievement1 = achievementRepository.save(achievement1);

        Achievement achievement2 = new Achievement();
        achievement2.setTitle("Achievement Two");
        achievement2.setDescription("Description for achievement two");
        achievement2 = achievementRepository.save(achievement2);

        UserAchievementId userAchievementId1 = new UserAchievementId(user.getId(), achievement1.getId());
        UserAchievement userAchievement1 = new UserAchievement();
        userAchievement1.setId(userAchievementId1);
        userAchievement1.setStudent(user);
        userAchievement1.setAchievement(achievement1);
        userAchievement1.setDateAchieved(LocalDate.now());
        userAchievement1 = userAchievementRepository.save(userAchievement1);

        UserAchievementId userAchievementId2 = new UserAchievementId(user.getId(), achievement2.getId());
        UserAchievement userAchievement2 = new UserAchievement();
        userAchievement2.setId(userAchievementId2);
        userAchievement2.setStudent(user);
        userAchievement2.setAchievement(achievement2);
        userAchievement2.setDateAchieved(LocalDate.now());
        userAchievement2 = userAchievementRepository.save(userAchievement2);

        List<UserAchievement> userAchievements = userAchievementRepository.findByStudentId(user.getId());
        assertEquals(2, userAchievements.size());
        Achievement finalAchievement = achievement1;
        assertTrue(userAchievements.stream().anyMatch(ua -> ua.getAchievement().getId().equals(finalAchievement.getId())));
        Achievement finalAchievement1 = achievement2;
        assertTrue(userAchievements.stream().anyMatch(ua -> ua.getAchievement().getId().equals(finalAchievement1.getId())));
    }
}