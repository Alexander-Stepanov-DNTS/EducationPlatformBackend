package ru.stepanov.EducationPlatform.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.LessonDto;
import ru.stepanov.EducationPlatform.mappers.CourseMapper;
import ru.stepanov.EducationPlatform.mappers.LessonMapper;
import ru.stepanov.EducationPlatform.models.Lesson;
import ru.stepanov.EducationPlatform.repositories.LessonRepository;
import ru.stepanov.EducationPlatform.services.LessonService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Override
    @Transactional(readOnly = true)
    public LessonDto getLessonById(Long id) {
        Optional<Lesson> lesson = lessonRepository.findById(id);
        return lesson.map(LessonMapper.INSTANCE::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LessonDto> getAllLessons() {
        return lessonRepository.findAll().stream()
                .map(LessonMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LessonDto createLesson(LessonDto lessonDto) {
        Lesson lesson = LessonMapper.INSTANCE.toEntity(lessonDto);
        lesson = lessonRepository.save(lesson);
        return LessonMapper.INSTANCE.toDto(lesson);
    }

    @Override
    @Transactional
    public LessonDto updateLesson(Long id, LessonDto lessonDto) {
        Optional<Lesson> existingLesson = lessonRepository.findById(id);
        if (existingLesson.isPresent()) {
            Lesson lesson = existingLesson.get();
            lesson.setName(lessonDto.getName());
            lesson.setNumber(lessonDto.getNumber());
            lesson.setVideoUrl(lessonDto.getVideoUrl());
            lesson.setLessonDetails(lessonDto.getLessonDetails());
            lesson.setCourseOrder(lessonDto.getCourseOrder());
            lesson.setCourse(CourseMapper.INSTANCE.toEntity(lessonDto.getCourse()));
            lesson = lessonRepository.save(lesson);
            return LessonMapper.INSTANCE.toDto(lesson);
        } else {
            return null; // или выбросить исключение
        }
    }

    @Override
    @Transactional
    public void deleteLesson(Long id) {
        lessonRepository.deleteById(id);
    }
}

