package ru.stepanov.EducationPlatform.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.StudentLessonDto;
import ru.stepanov.EducationPlatform.mappers.LessonMapper;
import ru.stepanov.EducationPlatform.mappers.StudentLessonMapper;
import ru.stepanov.EducationPlatform.mappers.UserMapper;
import ru.stepanov.EducationPlatform.models.EmbeddedId.StudentLessonId;
import ru.stepanov.EducationPlatform.models.Enrolment;
import ru.stepanov.EducationPlatform.models.StudentLesson;
import ru.stepanov.EducationPlatform.repositories.EnrolmentRepository;
import ru.stepanov.EducationPlatform.repositories.StudentLessonRepository;
import ru.stepanov.EducationPlatform.services.StudentLessonService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentLessonServiceImpl implements StudentLessonService {

    private final StudentLessonRepository studentLessonRepository;

    private final EnrolmentRepository enrolmentRepository;

    @Autowired
    public StudentLessonServiceImpl(StudentLessonRepository studentLessonRepository, EnrolmentRepository enrolmentRepository) {
        this.studentLessonRepository = studentLessonRepository;
        this.enrolmentRepository = enrolmentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public StudentLessonDto getStudentLessonById(Long studentId, Long lessonId) {
        StudentLessonId studentLessonId = new StudentLessonId(studentId, lessonId);
        Optional<StudentLesson> studentLesson = studentLessonRepository.findById(studentLessonId);
        return studentLesson.map(StudentLessonMapper.INSTANCE::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean studentLessonExists(Long studentId, Long lessonId) {
        StudentLessonId studentLessonId = new StudentLessonId(studentId, lessonId);
        return studentLessonRepository.existsById(studentLessonId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentLessonDto> getAllStudentLessons() {
        return studentLessonRepository.findAll().stream()
                .map(StudentLessonMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StudentLessonDto createStudentLesson(StudentLessonDto studentLessonDto) {
        StudentLesson studentLesson = StudentLessonMapper.INSTANCE.toEntity(studentLessonDto);

        StudentLessonId studentLessonId = new StudentLessonId(studentLessonDto.getStudent().getId(),
                studentLessonDto.getLesson().getId());
        studentLesson.setId(studentLessonId);

        studentLesson = studentLessonRepository.save(studentLesson);

        Optional<Enrolment> enrolmentOptional = enrolmentRepository.findByIdCourseIdAndIdStudentId
                (studentLesson.getLesson().getCourse().getId(), studentLessonDto.getStudent().getId());

        if (enrolmentOptional.isPresent()) {
            Enrolment enrolment = enrolmentOptional.get();
            enrolment.setProgress(enrolment.getProgress() + 1);
            enrolmentRepository.save(enrolment);
        }

        return StudentLessonMapper.INSTANCE.toDto(studentLesson);
    }

    @Override
    @Transactional
    public StudentLessonDto updateStudentLesson(Long studentId, Long lessonId, StudentLessonDto studentLessonDto) {
        StudentLessonId studentLessonId = new StudentLessonId(studentId, lessonId);
        Optional<StudentLesson> existingStudentLesson = studentLessonRepository.findById(studentLessonId);
        if (existingStudentLesson.isPresent()) {
            StudentLesson studentLesson = existingStudentLesson.get();
            studentLesson.setCompletedDatetime(studentLessonDto.getCompletedDatetime());
            studentLesson.setStudent(UserMapper.INSTANCE.toEntity(studentLessonDto.getStudent()));
            studentLesson.setLesson(LessonMapper.INSTANCE.toEntity(studentLessonDto.getLesson()));
            studentLesson = studentLessonRepository.save(studentLesson);
            return StudentLessonMapper.INSTANCE.toDto(studentLesson);
        } else {
            return null; // или выбросить исключение
        }
    }

    @Override
    @Transactional
    public void deleteStudentLesson(Long studentId, Long lessonId) {
        StudentLessonId studentLessonId = new StudentLessonId(studentId, lessonId);
        studentLessonRepository.deleteById(studentLessonId);
    }
}