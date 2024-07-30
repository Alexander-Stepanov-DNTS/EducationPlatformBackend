package ru.stepanov.EducationPlatform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stepanov.EducationPlatform.DTO.UserAchievementDto;
import ru.stepanov.EducationPlatform.services.UserAchievementService;

import java.util.List;

@RestController
@RequestMapping("/user-achievements")
public class UserAchievementController {

    @Autowired
    private UserAchievementService userAchievementService;

    @GetMapping("/{userId}/{achievementId}")
    public ResponseEntity<UserAchievementDto> getUserAchievementById(@PathVariable Long userId, @PathVariable Long achievementId) {
        UserAchievementDto userAchievement = userAchievementService.getUserAchievementById(userId, achievementId);
        return ResponseEntity.ok(userAchievement);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserAchievementDto>> getUserAchievementByUserId(@PathVariable Long userId) {
        List<UserAchievementDto> userAchievements = userAchievementService.getUserAchievementByUserId(userId);
        return ResponseEntity.ok(userAchievements);
    }

    @GetMapping
    public ResponseEntity<List<UserAchievementDto>> getAllUserAchievements() {
        List<UserAchievementDto> userAchievements = userAchievementService.getAllUserAchievements();
        return ResponseEntity.ok(userAchievements);
    }

    @PostMapping
    public ResponseEntity<UserAchievementDto> createUserAchievement(@RequestBody UserAchievementDto userAchievementDto) {
        UserAchievementDto createdUserAchievement = userAchievementService.createUserAchievement(userAchievementDto);
        return ResponseEntity.ok(createdUserAchievement);
    }

    @PutMapping("/{userId}/{achievementId}")
    public ResponseEntity<UserAchievementDto> updateUserAchievement(@PathVariable Long userId, @PathVariable Long achievementId, @RequestBody UserAchievementDto userAchievementDto) {
        UserAchievementDto updatedUserAchievement = userAchievementService.updateUserAchievement(userId, achievementId, userAchievementDto);
        return ResponseEntity.ok(updatedUserAchievement);
    }

    @DeleteMapping("/{userId}/{achievementId}")
    public ResponseEntity<Void> deleteUserAchievement(@PathVariable Long userId, @PathVariable Long achievementId) {
        userAchievementService.deleteUserAchievement(userId, achievementId);
        return ResponseEntity.noContent().build();
    }
}