package ru.stepanov.EducationPlatform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.stepanov.EducationPlatform.DTO.AchievementDto;
import ru.stepanov.EducationPlatform.services.AchievementService;

import java.util.List;

@RestController
@RequestMapping("/achievements")
public class AchievementController {

    private final AchievementService achievementService;

    @Autowired
    public AchievementController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AchievementDto> getAchievementById(@PathVariable Long id) {
        AchievementDto achievement = achievementService.getAchievementById(id);
        return ResponseEntity.ok(achievement);
    }

    @GetMapping
    public ResponseEntity<List<AchievementDto>> getAllAchievements() {
        List<AchievementDto> achievements = achievementService.getAllAchievements();
        return ResponseEntity.ok(achievements);
    }

    @PostMapping
    public ResponseEntity<AchievementDto> createAchievement(@RequestBody AchievementDto achievementDto) {
        AchievementDto createdAchievement = achievementService.createAchievement(achievementDto);
        return ResponseEntity.ok(createdAchievement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AchievementDto> updateAchievement(@PathVariable Long id, @RequestBody AchievementDto achievementDto) {
        AchievementDto updatedAchievement = achievementService.updateAchievement(id, achievementDto);
        return ResponseEntity.ok(updatedAchievement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAchievement(@PathVariable Long id) {
        achievementService.deleteAchievement(id);
        return ResponseEntity.noContent().build();
    }
}