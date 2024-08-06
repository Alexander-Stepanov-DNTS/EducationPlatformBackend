package ru.stepanov.EducationPlatform.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.EnrolmentDto;
import ru.stepanov.EducationPlatform.mappers.CourseMapper;
import ru.stepanov.EducationPlatform.mappers.EnrolmentMapper;
import ru.stepanov.EducationPlatform.mappers.UserMapper;
import ru.stepanov.EducationPlatform.models.EmbeddedId.EnrolmentId;
import ru.stepanov.EducationPlatform.models.Enrolment;
import ru.stepanov.EducationPlatform.repositories.EnrolmentRepository;
import ru.stepanov.EducationPlatform.repositories.UserRepository;
import ru.stepanov.EducationPlatform.services.EnrolmentService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnrolmentServiceImpl implements EnrolmentService {

    private final EnrolmentRepository enrolmentRepository;

    @Autowired
    public EnrolmentServiceImpl(EnrolmentRepository enrolmentRepository) {
        this.enrolmentRepository = enrolmentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public EnrolmentDto getEnrolmentById(Long courseId, Long studentId) {
        EnrolmentId enrolmentId = new EnrolmentId(courseId, studentId);
        Optional<Enrolment> enrolment = enrolmentRepository.findById(enrolmentId);
        return enrolment.map(EnrolmentMapper.INSTANCE::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnrolmentDto> getAllEnrolments() {
        return enrolmentRepository.findAll().stream()
                .map(EnrolmentMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EnrolmentDto createEnrolment(EnrolmentDto enrolmentDto) {
        Enrolment enrolment = EnrolmentMapper.INSTANCE.toEntity(enrolmentDto);

        EnrolmentId enrolmentId = new EnrolmentId(enrolmentDto.getCourse().getId(), enrolmentDto.getStudent().getId());
        enrolment.setId(enrolmentId);

        enrolment = enrolmentRepository.save(enrolment);
        return EnrolmentMapper.INSTANCE.toDto(enrolment);
    }

    @Override
    @Transactional
    public EnrolmentDto updateEnrolment(Long courseId, Long studentId, EnrolmentDto enrolmentDto) {
        EnrolmentId enrolmentId = new EnrolmentId(courseId, studentId);
        Optional<Enrolment> existingEnrolment = enrolmentRepository.findById(enrolmentId);
        if (existingEnrolment.isPresent()) {
            Enrolment enrolment = existingEnrolment.get();
            enrolment.setEnrolmentDatetime(enrolmentDto.getEnrolmentDatetime());
            enrolment.setCompletedDatetime(enrolmentDto.getCompletedDatetime());
            enrolment.setIsAuthor(enrolmentDto.getIsAuthor());
            enrolment.setCourse(CourseMapper.INSTANCE.toEntity(enrolmentDto.getCourse()));
            enrolment.setStudent(UserMapper.INSTANCE.toEntity(enrolmentDto.getStudent()));
            enrolment = enrolmentRepository.save(enrolment);
            return EnrolmentMapper.INSTANCE.toDto(enrolment);
        } else {
            return null; // или выбросить исключение
        }
    }

    @Override
    @Transactional
    public void deleteEnrolment(Long courseId, Long studentId) {
        EnrolmentId enrolmentId = new EnrolmentId(courseId, studentId);
        enrolmentRepository.deleteById(enrolmentId);
    }

    @Override
    @Transactional
    public boolean isEnrolled(Long courseId, Long studentId) {
        return enrolmentRepository.findByIdCourseIdAndIdStudentId(courseId, studentId).isPresent();
    }
}

