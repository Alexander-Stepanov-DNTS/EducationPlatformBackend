package ru.stepanov.EducationPlatform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.stepanov.EducationPlatform.DTO.CourseMaterialDto;
import ru.stepanov.EducationPlatform.services.CourseMaterialService;

import java.util.List;

@RestController
@RequestMapping("/course-materials")
public class CourseMaterialController {

    private final CourseMaterialService courseMaterialService;

    @Autowired
    public CourseMaterialController(CourseMaterialService courseMaterialService) {
        this.courseMaterialService = courseMaterialService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseMaterialDto> getCourseMaterialById(@PathVariable Long id) {
        CourseMaterialDto courseMaterial = courseMaterialService.getCourseMaterialById(id);
        return ResponseEntity.ok(courseMaterial);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<CourseMaterialDto>> getAllMaterialsByCourse(@PathVariable Long courseId) {
        List<CourseMaterialDto> materials = courseMaterialService.getAllMaterialsByCourse(courseId);
        return ResponseEntity.ok(materials);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CourseMaterialDto>> getAllMaterialsByUser(@PathVariable Long userId) {
        List<CourseMaterialDto> materials = courseMaterialService.getAllMaterialsByUser(userId);
        return ResponseEntity.ok(materials);
    }

    @GetMapping
    public ResponseEntity<List<CourseMaterialDto>> getAllCourseMaterials() {
        List<CourseMaterialDto> materials = courseMaterialService.getAllCourseMaterials();
        return ResponseEntity.ok(materials);
    }

    @PostMapping
    @CrossOrigin(origins = "http://localhost:5173/")
    public ResponseEntity<CourseMaterialDto> createCourseMaterial(@RequestBody CourseMaterialDto courseMaterialDto) {
        CourseMaterialDto createdMaterial = courseMaterialService.createCourseMaterial(courseMaterialDto);
        return ResponseEntity.ok(createdMaterial);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseMaterialDto> updateCourseMaterial(@PathVariable Long id, @RequestBody CourseMaterialDto courseMaterialDto) {
        CourseMaterialDto updatedMaterial = courseMaterialService.updateCourseMaterial(id, courseMaterialDto);
        return ResponseEntity.ok(updatedMaterial);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourseMaterial(@PathVariable Long id) {
        courseMaterialService.deleteCourseMaterial(id);
        return ResponseEntity.noContent().build();
    }
}