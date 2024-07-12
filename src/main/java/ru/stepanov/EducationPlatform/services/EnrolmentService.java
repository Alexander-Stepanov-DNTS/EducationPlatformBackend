package ru.stepanov.EducationPlatform.services;

import ru.stepanov.EducationPlatform.DTO.EnrolmentDto;

import java.util.List;

public interface EnrolmentService {
    EnrolmentDto getEnrolmentById(Long courseId, Long studentId);
    List<EnrolmentDto> getAllEnrolments();
    EnrolmentDto createEnrolment(EnrolmentDto enrolmentDto);
    EnrolmentDto updateEnrolment(Long courseId, Long studentId, EnrolmentDto enrolmentDto);
    void deleteEnrolment(Long courseId, Long studentId);
    boolean isEnrolled(Long courseId, Long studentId);
}
