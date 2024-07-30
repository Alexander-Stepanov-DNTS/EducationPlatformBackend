package ru.stepanov.EducationPlatform.repositories;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import ru.stepanov.EducationPlatform.models.Achievement;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AchievementRepositoryTest {

    @Autowired
    private AchievementRepository achievementRepository;

    @Test
    public void testSaveAndFindById() {
        Achievement achievement = new Achievement();
        achievement.setTitle("First Achievement");
        achievement.setDescription("Description for first achievement");

        Achievement savedAchievement = achievementRepository.save(achievement);

        assertNotNull(savedAchievement.getId());

        Optional<Achievement> foundAchievement = achievementRepository.findById(savedAchievement.getId());
        assertTrue(foundAchievement.isPresent());
        assertEquals("First Achievement", foundAchievement.get().getTitle());
        assertEquals("Description for first achievement", foundAchievement.get().getDescription());
    }
}