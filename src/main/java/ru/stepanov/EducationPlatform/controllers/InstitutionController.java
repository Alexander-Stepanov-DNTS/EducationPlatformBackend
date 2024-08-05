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
import ru.stepanov.EducationPlatform.DTO.InstitutionDto;
import ru.stepanov.EducationPlatform.services.InstitutionService;

import java.util.List;

@RestController
@RequestMapping("/institutions")
public class InstitutionController {

    private final InstitutionService institutionService;

    @Autowired
    public InstitutionController(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }

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