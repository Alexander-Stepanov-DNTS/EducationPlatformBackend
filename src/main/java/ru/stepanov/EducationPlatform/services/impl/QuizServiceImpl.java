package ru.stepanov.EducationPlatform.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.QuizAnswerDto;
import ru.stepanov.EducationPlatform.DTO.QuizDto;
import ru.stepanov.EducationPlatform.DTO.QuizQuestionDto;
import ru.stepanov.EducationPlatform.mappers.CourseMapper;
import ru.stepanov.EducationPlatform.mappers.QuizAnswerMapper;
import ru.stepanov.EducationPlatform.mappers.QuizMapper;
import ru.stepanov.EducationPlatform.mappers.QuizQuestionMapper;
import ru.stepanov.EducationPlatform.models.Quiz;
import ru.stepanov.EducationPlatform.models.QuizAnswer;
import ru.stepanov.EducationPlatform.models.QuizQuestion;
import ru.stepanov.EducationPlatform.repositories.QuizAnswerRepository;
import ru.stepanov.EducationPlatform.repositories.QuizQuestionRepository;
import ru.stepanov.EducationPlatform.repositories.QuizRepository;
import ru.stepanov.EducationPlatform.services.QuizService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;

    private final QuizQuestionRepository quizQuestionRepository;

    private final QuizAnswerRepository quizAnswerRepository;

    @Autowired
    public QuizServiceImpl(QuizRepository quizRepository, QuizQuestionRepository quizQuestionRepository, QuizAnswerRepository quizAnswerRepository) {
        this.quizRepository = quizRepository;
        this.quizQuestionRepository = quizQuestionRepository;
        this.quizAnswerRepository = quizAnswerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public QuizDto getQuizById(Long id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        return quiz.map(QuizMapper.INSTANCE::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuizDto> getQuizzesFromCourse(Long id) {
        return quizRepository.findByCourseId(id).stream()
                .map(QuizMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
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
            return null;
        }
    }

    @Override
    @Transactional
    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }

    @Override
    public List<QuizQuestionDto> getQuizQuestions(Long quizId) {
        List<QuizQuestion> questions = quizQuestionRepository.findByQuizId(quizId);
        return questions.stream()
                .map(QuizQuestionMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public List<QuizQuestionDto> getQuizQuestionsWithAnswers(Long quizId) {
        List<QuizQuestion> questions = quizQuestionRepository.findByQuizId(quizId);
        List<QuizQuestionDto> questionDtos = new ArrayList<>();

        for (QuizQuestion question : questions) {
            QuizQuestionDto questionDto = QuizQuestionMapper.INSTANCE.toDto(question);
            List<QuizAnswer> answers = quizAnswerRepository.findByQuestionId(question.getId());
            List<QuizAnswerDto> answerDtos = answers.stream()
                    .map(QuizAnswerMapper.INSTANCE::toDto)
                    .collect(Collectors.toList());
            questionDto.setOptions(answerDtos);
            questionDtos.add(questionDto);
        }
        return questionDtos;
    }
}