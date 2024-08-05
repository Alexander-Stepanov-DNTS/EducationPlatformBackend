package ru.stepanov.EducationPlatform.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.CourseMaterialDto;
import ru.stepanov.EducationPlatform.mappers.CourseMaterialMapper;
import ru.stepanov.EducationPlatform.models.Course;
import ru.stepanov.EducationPlatform.models.CourseMaterial;
import ru.stepanov.EducationPlatform.models.Enrolment;
import ru.stepanov.EducationPlatform.repositories.CourseMaterialRepository;
import ru.stepanov.EducationPlatform.repositories.EnrolmentRepository;
import ru.stepanov.EducationPlatform.services.CourseMaterialService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseMaterialServiceImpl implements CourseMaterialService {

    private final CourseMaterialRepository courseMaterialRepository;

    private final EnrolmentRepository enrollmentRepository;

    @Autowired
    public CourseMaterialServiceImpl(CourseMaterialRepository courseMaterialRepository, EnrolmentRepository enrollmentRepository) {
        this.courseMaterialRepository = courseMaterialRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public CourseMaterialDto getCourseMaterialById(Long id) {
        Optional<CourseMaterial> courseMaterial = courseMaterialRepository.findById(id);
        return courseMaterial.map(CourseMaterialMapper.INSTANCE::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseMaterialDto> getAllMaterialsByCourse(Long courseId) {
        return courseMaterialRepository.findByCourseId(courseId).stream()
                .map(CourseMaterialMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseMaterialDto> getAllMaterialsByUser(Long userId) {
        List<Enrolment> enrollments = enrollmentRepository.findByStudentId(userId);

        List<Long> courseIds = new ArrayList<>();
        for (Enrolment enrollment : enrollments) {
            Course course = enrollment.getCourse();
            courseIds.add(course.getId());
            System.out.println(course.getId());
        }

        List<CourseMaterial> materials = courseMaterialRepository.findByCourseIdIn(courseIds);

        return materials.stream()
                .map(CourseMaterialMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseMaterialDto> getAllCourseMaterials() {
        return courseMaterialRepository.findAll().stream()
                .map(CourseMaterialMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CourseMaterialDto createCourseMaterial(CourseMaterialDto courseMaterialDto) {
        CourseMaterial courseMaterial = CourseMaterialMapper.INSTANCE.toEntity(courseMaterialDto);
        courseMaterial = courseMaterialRepository.save(courseMaterial);
        return CourseMaterialMapper.INSTANCE.toDto(courseMaterial);
    }

    @Override
    @Transactional
    public CourseMaterialDto updateCourseMaterial(Long id, CourseMaterialDto courseMaterialDto) {
        Optional<CourseMaterial> existingMaterial = courseMaterialRepository.findById(id);
        if (existingMaterial.isPresent()) {
            CourseMaterial courseMaterial = existingMaterial.get();
            courseMaterial.setMaterialTitle(courseMaterialDto.getMaterialTitle());
            courseMaterial.setMaterialUrl(courseMaterialDto.getMaterialUrl());
            courseMaterial = courseMaterialRepository.save(courseMaterial);
            return CourseMaterialMapper.INSTANCE.toDto(courseMaterial);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void deleteCourseMaterial(Long id) {
        courseMaterialRepository.deleteById(id);
    }
}