package ru.stepanov.EducationPlatform.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.QuizAnswerDto;
import ru.stepanov.EducationPlatform.mappers.QuizAnswerMapper;
import ru.stepanov.EducationPlatform.mappers.QuizQuestionMapper;
import ru.stepanov.EducationPlatform.models.QuizAnswer;
import ru.stepanov.EducationPlatform.repositories.QuizAnswerRepository;
import ru.stepanov.EducationPlatform.services.QuizAnswerService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizAnswerServiceImpl implements QuizAnswerService {

    private final QuizAnswerRepository quizAnswerRepository;

    @Autowired
    public QuizAnswerServiceImpl(QuizAnswerRepository quizAnswerRepository) {
        this.quizAnswerRepository = quizAnswerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public QuizAnswerDto getQuizAnswerById(Long id) {
        Optional<QuizAnswer> quizAnswer = quizAnswerRepository.findById(id);
        return quizAnswer.map(QuizAnswerMapper.INSTANCE::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuizAnswerDto> getAllQuizAnswers() {
        return quizAnswerRepository.findAll().stream()
                .map(QuizAnswerMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public QuizAnswerDto createQuizAnswer(QuizAnswerDto quizAnswerDto) {
        QuizAnswer quizAnswer = QuizAnswerMapper.INSTANCE.toEntity(quizAnswerDto);
        quizAnswer = quizAnswerRepository.save(quizAnswer);
        return QuizAnswerMapper.INSTANCE.toDto(quizAnswer);
    }

    @Override
    @Transactional
    public QuizAnswerDto updateQuizAnswer(Long id, QuizAnswerDto quizAnswerDto) {
        Optional<QuizAnswer> existingQuizAnswer = quizAnswerRepository.findById(id);
        if (existingQuizAnswer.isPresent()) {
            QuizAnswer quizAnswer = existingQuizAnswer.get();
            quizAnswer.setAnswerText(quizAnswerDto.getAnswerText());
            quizAnswer.setIsCorrect(quizAnswerDto.getIsCorrect());
            quizAnswer.setQuestion(QuizQuestionMapper.INSTANCE.toEntity(quizAnswerDto.getQuestion()));
            quizAnswer = quizAnswerRepository.save(quizAnswer);
            return QuizAnswerMapper.INSTANCE.toDto(quizAnswer);
        } else {
            return null; // или выбросить исключение
        }
    }

    @Override
    @Transactional
    public void deleteQuizAnswer(Long id) {
        quizAnswerRepository.deleteById(id);
    }
}