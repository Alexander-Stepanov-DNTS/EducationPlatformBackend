package ru.stepanov.EducationPlatform.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.stepanov.EducationPlatform.DTO.QuizDto;
import ru.stepanov.EducationPlatform.DTO.StudentQuizAttemptDto;
import ru.stepanov.EducationPlatform.DTO.UserDto;
import ru.stepanov.EducationPlatform.services.impl.StudentQuizAttemptServiceImpl;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class StudentQuizAttemptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentQuizAttemptServiceImpl studentQuizAttemptService;

    private StudentQuizAttemptDto studentQuizAttemptDto;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        // Регистрация модуля JavaTime в ObjectMapper
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        studentQuizAttemptDto = new StudentQuizAttemptDto();
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmailAddress("student@example.com");
        userDto.setLogin("Student");
        userDto.setPassword("Password");

        QuizDto quizDto = new QuizDto();
        quizDto.setId(1L);
        quizDto.setTitle("Quiz Title");
        quizDto.setDescription("Quiz Description");
        quizDto.setCreatedDate(LocalDateTime.now());
        quizDto.setIsActive(true);
        quizDto.setCourseOrder(1);

        studentQuizAttemptDto.setStudent(userDto);
        studentQuizAttemptDto.setQuiz(quizDto);
        studentQuizAttemptDto.setAttemptDatetime(LocalDateTime.now());
        studentQuizAttemptDto.setScoreAchieved(85);

        when(studentQuizAttemptService.getStudentQuizAttemptById(anyLong(), anyLong(), any(LocalDateTime.class))).thenReturn(studentQuizAttemptDto);
        when(studentQuizAttemptService.getAllStudentQuizAttempts()).thenReturn(Collections.singletonList(studentQuizAttemptDto));
        when(studentQuizAttemptService.createStudentQuizAttempt(any(StudentQuizAttemptDto.class))).thenReturn(studentQuizAttemptDto);
        when(studentQuizAttemptService.updateStudentQuizAttempt(anyLong(), anyLong(), any(LocalDateTime.class), any(StudentQuizAttemptDto.class))).thenReturn(studentQuizAttemptDto);
        doNothing().when(studentQuizAttemptService).deleteStudentQuizAttempt(anyLong(), anyLong(), any(LocalDateTime.class));
    }

    @Test
    public void testGetStudentQuizAttemptById() throws Exception {
        mockMvc.perform(get("/student-quiz-attempts/1/1/" + studentQuizAttemptDto.getAttemptDatetime()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.student.id").value(1L))
                .andExpect(jsonPath("$.quiz.id").value(1L))
                .andExpect(jsonPath("$.scoreAchieved").value(85));
    }

    @Test
    public void testGetAllStudentQuizAttempts() throws Exception {
        mockMvc.perform(get("/student-quiz-attempts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].student.id").value(1L))
                .andExpect(jsonPath("$[0].quiz.id").value(1L))
                .andExpect(jsonPath("$[0].scoreAchieved").value(85));
    }

    @Test
    public void testCreateStudentQuizAttempt() throws Exception {
        mockMvc.perform(post("/student-quiz-attempts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentQuizAttemptDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.student.id").value(1L))
                .andExpect(jsonPath("$.quiz.id").value(1L))
                .andExpect(jsonPath("$.scoreAchieved").value(85));
    }

    @Test
    public void testUpdateStudentQuizAttempt() throws Exception {
        mockMvc.perform(put("/student-quiz-attempts/1/1/" + studentQuizAttemptDto.getAttemptDatetime())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentQuizAttemptDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.student.id").value(1L))
                .andExpect(jsonPath("$.quiz.id").value(1L))
                .andExpect(jsonPath("$.scoreAchieved").value(85));
    }

    @Test
    public void testDeleteStudentQuizAttempt() throws Exception {
        mockMvc.perform(delete("/student-quiz-attempts/1/1/" + studentQuizAttemptDto.getAttemptDatetime()))
                .andExpect(status().isNoContent());
    }
}