package ru.stepanov.EducationPlatform.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.QuizDto;
import ru.stepanov.EducationPlatform.mappers.CourseMapper;
import ru.stepanov.EducationPlatform.mappers.QuizMapper;
import ru.stepanov.EducationPlatform.models.Quiz;
import ru.stepanov.EducationPlatform.repositories.QuizRepository;
import ru.stepanov.EducationPlatform.services.QuizService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Override
    @Transactional(readOnly = true)
    public QuizDto getQuizById(Long id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        return quiz.map(QuizMapper.INSTANCE::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuizDto> getAllQuizzes() {
        return quizRepository.findAll().stream()
                .map(QuizMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public QuizDto createQuiz(QuizDto quizDto) {
        Quiz quiz = QuizMapper.INSTANCE.toEntity(quizDto);
        quiz = quizRepository.save(quiz);
        return QuizMapper.INSTANCE.toDto(quiz);
    }

    @Override
    @Transactional
    public QuizDto updateQuiz(Long id, QuizDto quizDto) {
        Optional<Quiz> existingQuiz = quizRepository.findById(id);
        if (existingQuiz.isPresent()) {
            Quiz quiz = existingQuiz.get();
            quiz.setTitle(quizDto.getTitle());
            quiz.setDescription(quizDto.getDescription());
            quiz.setCreatedDate(quizDto.getCreatedDate());
            quiz.setIsActive(quizDto.getIsActive());
            quiz.setDueDate(quizDto.getDueDate());
            quiz.setReminderDate(quizDto.getReminderDate());
            quiz.setCourseOrder(quizDto.getCourseOrder());
            quiz.setCourse(CourseMapper.INSTANCE.toEntity(quizDto.getCourse()));
            quiz = quizRepository.save(quiz);
            return QuizMapper.INSTANCE.toDto(quiz);
        } else {
            return null; // или выбросить исключение
        }
    }

    @Override
    @Transactional
    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }
}
