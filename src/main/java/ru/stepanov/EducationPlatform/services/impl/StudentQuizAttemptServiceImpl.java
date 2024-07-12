package ru.stepanov.EducationPlatform.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.StudentQuizAttemptDto;
import ru.stepanov.EducationPlatform.mappers.QuizMapper;
import ru.stepanov.EducationPlatform.mappers.StudentQuizAttemptMapper;
import ru.stepanov.EducationPlatform.mappers.UserMapper;
import ru.stepanov.EducationPlatform.models.EmbeddedId.StudentQuizAttemptId;
import ru.stepanov.EducationPlatform.models.Quiz;
import ru.stepanov.EducationPlatform.models.StudentQuizAttempt;
import ru.stepanov.EducationPlatform.models.User;
import ru.stepanov.EducationPlatform.repositories.QuizRepository;
import ru.stepanov.EducationPlatform.repositories.StudentQuizAttemptRepository;
import ru.stepanov.EducationPlatform.repositories.UserRepository;
import ru.stepanov.EducationPlatform.services.StudentQuizAttemptService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentQuizAttemptServiceImpl implements StudentQuizAttemptService {

    @Autowired
    private StudentQuizAttemptRepository studentQuizAttemptRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    public QuizRepository quizRepository;

    @Override
    @Transactional(readOnly = true)
    public StudentQuizAttemptDto getStudentQuizAttemptById(Long studentId, Long quizId, LocalDateTime attemptDatetime) {
        StudentQuizAttemptId studentQuizAttemptId = new StudentQuizAttemptId(studentId, quizId, attemptDatetime);
        Optional<StudentQuizAttempt> studentQuizAttempt = studentQuizAttemptRepository.findById(studentQuizAttemptId);
        return studentQuizAttempt.map(StudentQuizAttemptMapper.INSTANCE::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentQuizAttemptDto> getAllStudentQuizAttempts() {
        return studentQuizAttemptRepository.findAll().stream()
                .map(StudentQuizAttemptMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StudentQuizAttemptDto createStudentQuizAttempt(StudentQuizAttemptDto studentQuizAttemptDto) {
        System.out.println(studentQuizAttemptDto);
        StudentQuizAttempt attempt = new StudentQuizAttempt();

        User user = userRepository.findById(studentQuizAttemptDto.getStudent().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Quiz quiz = quizRepository.findById(studentQuizAttemptDto.getQuiz().getId())
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        attempt.setStudent(user);
        attempt.setQuiz(quiz);
        LocalDateTime data = LocalDateTime.now();
        attempt.setAttemptDatetime(data);
        attempt.setId(new StudentQuizAttemptId(user.getId(), quiz.getId(), data));
        attempt.setScoreAchieved(studentQuizAttemptDto.getScoreAchieved());

        attempt = studentQuizAttemptRepository.save(attempt);

        return StudentQuizAttemptMapper.INSTANCE.toDto(attempt);
    }

    @Override
    @Transactional
    public StudentQuizAttemptDto updateStudentQuizAttempt(Long studentId, Long quizId, LocalDateTime attemptDatetime, StudentQuizAttemptDto studentQuizAttemptDto) {
        StudentQuizAttemptId studentQuizAttemptId = new StudentQuizAttemptId(studentId, quizId, attemptDatetime);
        Optional<StudentQuizAttempt> existingStudentQuizAttempt = studentQuizAttemptRepository.findById(studentQuizAttemptId);
        if (existingStudentQuizAttempt.isPresent()) {
            StudentQuizAttempt studentQuizAttempt = existingStudentQuizAttempt.get();
            studentQuizAttempt.setScoreAchieved(studentQuizAttemptDto.getScoreAchieved());
            studentQuizAttempt.setStudent(UserMapper.INSTANCE.toEntity(studentQuizAttemptDto.getStudent()));
            studentQuizAttempt.setQuiz(QuizMapper.INSTANCE.toEntity(studentQuizAttemptDto.getQuiz()));
            studentQuizAttempt.setAttemptDatetime(studentQuizAttemptDto.getAttemptDatetime());
            studentQuizAttempt = studentQuizAttemptRepository.save(studentQuizAttempt);
            return StudentQuizAttemptMapper.INSTANCE.toDto(studentQuizAttempt);
        } else {
            return null; // или выбросить исключение
        }
    }

    @Override
    @Transactional
    public void deleteStudentQuizAttempt(Long studentId, Long quizId, LocalDateTime attemptDatetime) {
        StudentQuizAttemptId studentQuizAttemptId = new StudentQuizAttemptId(studentId, quizId, attemptDatetime);
        studentQuizAttemptRepository.deleteById(studentQuizAttemptId);
    }
}
