package ru.stepanov.EducationPlatform.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.StudentLessonDto;
import ru.stepanov.EducationPlatform.mappers.LessonMapper;
import ru.stepanov.EducationPlatform.mappers.StudentLessonMapper;
import ru.stepanov.EducationPlatform.mappers.UserMapper;
import ru.stepanov.EducationPlatform.models.EmbeddedId.StudentLessonId;
import ru.stepanov.EducationPlatform.models.StudentLesson;
import ru.stepanov.EducationPlatform.repositories.StudentLessonRepository;
import ru.stepanov.EducationPlatform.services.StudentLessonService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentLessonServiceImpl implements StudentLessonService {

    @Autowired
    private StudentLessonRepository studentLessonRepository;

    @Override
    @Transactional(readOnly = true)
    public StudentLessonDto getStudentLessonById(Long studentId, Long lessonId) {
        StudentLessonId studentLessonId = new StudentLessonId(studentId, lessonId);
        Optional<StudentLesson> studentLesson = studentLessonRepository.findById(studentLessonId);
        return studentLesson.map(StudentLessonMapper.INSTANCE::toDto).orElse(null);
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
        studentLesson = studentLessonRepository.save(studentLesson);
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
