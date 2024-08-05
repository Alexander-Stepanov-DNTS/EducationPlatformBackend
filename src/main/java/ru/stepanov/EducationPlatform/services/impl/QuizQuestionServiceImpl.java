package ru.stepanov.EducationPlatform.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.QuizAnswerDto;
import ru.stepanov.EducationPlatform.DTO.QuizQuestionDto;
import ru.stepanov.EducationPlatform.mappers.QuizAnswerMapper;
import ru.stepanov.EducationPlatform.mappers.QuizMapper;
import ru.stepanov.EducationPlatform.mappers.QuizQuestionMapper;
import ru.stepanov.EducationPlatform.models.QuizAnswer;
import ru.stepanov.EducationPlatform.models.QuizQuestion;
import ru.stepanov.EducationPlatform.repositories.QuizAnswerRepository;
import ru.stepanov.EducationPlatform.repositories.QuizQuestionRepository;
import ru.stepanov.EducationPlatform.services.QuizQuestionService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizQuestionServiceImpl implements QuizQuestionService {

    @Autowired
    private QuizQuestionRepository quizQuestionRepository;

    @Autowired
    private QuizAnswerRepository quizAnswerRepository;

    @Override
    @Transactional(readOnly = true)
    public QuizQuestionDto getQuizQuestionById(Long id) {
        Optional<QuizQuestion> quizQuestion = quizQuestionRepository.findById(id);
        return quizQuestion.map(QuizQuestionMapper.INSTANCE::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuizQuestionDto> getAllQuizQuestions() {
        return quizQuestionRepository.findAll().stream()
                .map(QuizQuestionMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public QuizQuestionDto createQuizQuestion(QuizQuestionDto quizQuestionDto) {
        QuizQuestion quizQuestion = QuizQuestionMapper.INSTANCE.toEntity(quizQuestionDto);
        quizQuestion = quizQuestionRepository.save(quizQuestion);
        return QuizQuestionMapper.INSTANCE.toDto(quizQuestion);
    }

    @Override
    @Transactional
    public QuizQuestionDto updateQuizQuestion(Long id, QuizQuestionDto quizQuestionDto) {
        Optional<QuizQuestion> existingQuizQuestion = quizQuestionRepository.findById(id);
        if (existingQuizQuestion.isPresent()) {
            QuizQuestion quizQuestion = existingQuizQuestion.get();
            quizQuestion.setQuestionTitle(quizQuestionDto.getQuestionTitle());
            quizQuestion.setManyAnswers(quizQuestionDto.getManyAnswers());
            quizQuestion.setQuiz(QuizMapper.INSTANCE.toEntity(quizQuestionDto.getQuiz()));
            quizQuestion = quizQuestionRepository.save(quizQuestion);
            return QuizQuestionMapper.INSTANCE.toDto(quizQuestion);
        } else {
            return null; // или выбросить исключение
        }
    }

    @Override
    @Transactional
    public void deleteQuizQuestion(Long id) {
        quizQuestionRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<QuizAnswerDto> getQuestionAnswers(Long questionId){
        List<QuizAnswer> answers = quizAnswerRepository.findByQuestion_Id(questionId);
        return answers.stream()
                .map(QuizAnswerMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }
}