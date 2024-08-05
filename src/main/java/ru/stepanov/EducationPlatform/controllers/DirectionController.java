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
import ru.stepanov.EducationPlatform.DTO.DirectionDto;
import ru.stepanov.EducationPlatform.services.DirectionService;

import java.util.List;

@RestController
@RequestMapping("/directions")
public class DirectionController {

    private final DirectionService directionService;

    @Autowired
    public DirectionController(DirectionService directionService) {
        this.directionService = directionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectionDto> getDirectionById(@PathVariable Long id) {
        DirectionDto direction = directionService.getDirectionById(id);
        return ResponseEntity.ok(direction);
    }

    @GetMapping
    public ResponseEntity<List<DirectionDto>> getAllDirections() {
        List<DirectionDto> directions = directionService.getAllDirections();
        return ResponseEntity.ok(directions);
    }

    @PostMapping
    public ResponseEntity<DirectionDto> createDirection(@RequestBody DirectionDto directionDto) {
        DirectionDto createdDirection = directionService.createDirection(directionDto);
        return ResponseEntity.ok(createdDirection);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DirectionDto> updateDirection(@PathVariable Long id, @RequestBody DirectionDto directionDto) {
        DirectionDto updatedDirection = directionService.updateDirection(id, directionDto);
        return ResponseEntity.ok(updatedDirection);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDirection(@PathVariable Long id) {
        directionService.deleteDirection(id);
        return ResponseEntity.noContent().build();
    }
}