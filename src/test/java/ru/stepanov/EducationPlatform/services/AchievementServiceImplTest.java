package ru.stepanov.EducationPlatform.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.stepanov.EducationPlatform.DTO.AchievementDto;
import ru.stepanov.EducationPlatform.mappers.AchievementMapper;
import ru.stepanov.EducationPlatform.models.Achievement;
import ru.stepanov.EducationPlatform.repositories.AchievementRepository;
import ru.stepanov.EducationPlatform.services.impl.AchievementServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AchievementServiceImplTest {

    @Mock
    private AchievementRepository achievementRepository;

    @InjectMocks
    private AchievementServiceImpl achievementService;

    private Achievement achievement;
    private AchievementDto achievementDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        achievement = new Achievement();
        achievement.setId(1L);
        achievement.setTitle("Achievement Title");
        achievement.setDescription("Achievement Description");

        achievementDto = AchievementMapper.INSTANCE.toDto(achievement);
    }

    @Test
    public void testGetAchievementById() {
        when(achievementRepository.findById(anyLong())).thenReturn(Optional.of(achievement));

        AchievementDto foundAchievement = achievementService.getAchievementById(1L);

        assertNotNull(foundAchievement);
        assertEquals(achievementDto.getId(), foundAchievement.getId());
        assertEquals(achievementDto.getTitle(), foundAchievement.getTitle());
    }

    @Test
    public void testGetAllAchievements() {
        when(achievementRepository.findAll()).thenReturn(Collections.singletonList(achievement));

        List<AchievementDto> achievements = achievementService.getAllAchievements();

        assertNotNull(achievements);
        assertEquals(1, achievements.size());
        assertEquals(achievementDto.getTitle(), achievements.get(0).getTitle());
    }

    @Test
    public void testCreateAchievement() {
        when(achievementRepository.save(any(Achievement.class))).thenReturn(achievement);

        AchievementDto createdAchievement = achievementService.createAchievement(achievementDto);

        assertNotNull(createdAchievement);
        assertEquals(achievementDto.getTitle(), createdAchievement.getTitle());
    }

    @Test
    public void testUpdateAchievement() {
        when(achievementRepository.findById(anyLong())).thenReturn(Optional.of(achievement));
        when(achievementRepository.save(any(Achievement.class))).thenReturn(achievement);

        AchievementDto updatedAchievement = achievementService.updateAchievement(1L, achievementDto);

        assertNotNull(updatedAchievement);
        assertEquals(achievementDto.getTitle(), updatedAchievement.getTitle());
    }

    @Test
    public void testDeleteAchievement() {
        doNothing().when(achievementRepository).deleteById(anyLong());

        achievementService.deleteAchievement(1L);

        verify(achievementRepository, times(1)).deleteById(1L);
    }
}