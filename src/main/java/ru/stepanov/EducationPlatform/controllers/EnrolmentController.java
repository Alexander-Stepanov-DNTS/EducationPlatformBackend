package ru.stepanov.EducationPlatform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stepanov.EducationPlatform.DTO.EnrolmentDto;
import ru.stepanov.EducationPlatform.services.EnrolmentService;

import java.util.List;

@RestController
@RequestMapping("/enrolments")
public class EnrolmentController {

    @Autowired
    private EnrolmentService enrolmentService;

    @GetMapping("/{courseId}/{studentId}")
    public ResponseEntity<EnrolmentDto> getEnrolmentById(@PathVariable Long courseId, @PathVariable Long studentId) {
        EnrolmentDto enrolment = enrolmentService.getEnrolmentById(courseId, studentId);
        return ResponseEntity.ok(enrolment);
    }

    @GetMapping
    public ResponseEntity<List<EnrolmentDto>> getAllEnrolments() {
        List<EnrolmentDto> enrolments = enrolmentService.getAllEnrolments();
        return ResponseEntity.ok(enrolments);
    }

    @PostMapping
    public ResponseEntity<EnrolmentDto> createEnrolment(@RequestBody EnrolmentDto enrolmentDto) {
        EnrolmentDto createdEnrolment = enrolmentService.createEnrolment(enrolmentDto);
        return ResponseEntity.ok(createdEnrolment);
    }

    @PutMapping("/{courseId}/{studentId}")
    public ResponseEntity<EnrolmentDto> updateEnrolment(@PathVariable Long courseId, @PathVariable Long studentId, @RequestBody EnrolmentDto enrolmentDto) {
        EnrolmentDto updatedEnrolment = enrolmentService.updateEnrolment(courseId, studentId, enrolmentDto);
        return ResponseEntity.ok(updatedEnrolment);
    }

    @DeleteMapping("/{courseId}/{studentId}")
    public ResponseEntity<Void> deleteEnrolment(@PathVariable Long courseId, @PathVariable Long studentId) {
        enrolmentService.deleteEnrolment(courseId, studentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/isEnrolled")
    public ResponseEntity<Boolean> isEnrolled(@RequestParam Long courseId, @RequestParam Long studentId) {
        boolean isEnrolled = enrolmentService.isEnrolled(courseId, studentId);
        return ResponseEntity.ok(isEnrolled);
    }
}