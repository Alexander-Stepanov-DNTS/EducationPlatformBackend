package ru.stepanov.EducationPlatform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stepanov.EducationPlatform.DTO.InstitutionDto;
import ru.stepanov.EducationPlatform.services.InstitutionService;

import java.util.List;

@RestController
@RequestMapping("/institutions")
public class InstitutionController {

    @Autowired
    private InstitutionService institutionService;

    @GetMapping("/{id}")
    public ResponseEntity<InstitutionDto> getInstitutionById(@PathVariable Long id) {
        InstitutionDto institution = institutionService.getInstitutionById(id);
        return ResponseEntity.ok(institution);
    }

    @GetMapping
    public ResponseEntity<List<InstitutionDto>> getAllInstitutions() {
        List<InstitutionDto> institutions = institutionService.getAllInstitutions();
        return ResponseEntity.ok(institutions);
    }

    @PostMapping
    public ResponseEntity<InstitutionDto> createInstitution(@RequestBody InstitutionDto institutionDto) {
        InstitutionDto createdInstitution = institutionService.createInstitution(institutionDto);
        return ResponseEntity.ok(createdInstitution);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstitutionDto> updateInstitution(@PathVariable Long id, @RequestBody InstitutionDto institutionDto) {
        InstitutionDto updatedInstitution = institutionService.updateInstitution(id, institutionDto);
        return ResponseEntity.ok(updatedInstitution);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstitution(@PathVariable Long id) {
        institutionService.deleteInstitution(id);
        return ResponseEntity.noContent().build();
    }
}