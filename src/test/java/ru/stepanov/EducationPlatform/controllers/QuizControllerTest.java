package ru.stepanov.EducationPlatform.controllers;

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
import ru.stepanov.EducationPlatform.DTO.QuizQuestionDto;
import ru.stepanov.EducationPlatform.services.impl.QuizServiceImpl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuizServiceImpl quizService;

    @BeforeEach
    public void setup() {
        QuizDto quizDto = new QuizDto();
        quizDto.setId(1L);
        quizDto.setTitle("Sample Quiz");
        quizDto.setDescription("This is a sample quiz");
        quizDto.setCreatedDate(LocalDateTime.now());
        quizDto.setIsActive(true);
        quizDto.setDueDate(new Date());
        quizDto.setReminderDate(new Date());
        quizDto.setCourseOrder(1);

        QuizQuestionDto quizQuestionDto = new QuizQuestionDto();
        quizQuestionDto.setId(1L);
        quizQuestionDto.setQuestionTitle("Sample Question");
        quizQuestionDto.setManyAnswers(false);

        when(quizService.getQuizById(anyLong())).thenReturn(quizDto);
        when(quizService.getAllQuizzes()).thenReturn(List.of(quizDto));
        when(quizService.createQuiz(any(QuizDto.class))).thenReturn(quizDto);
        when(quizService.updateQuiz(anyLong(), any(QuizDto.class))).thenReturn(quizDto);
        when(quizService.getQuizQuestions(anyLong())).thenReturn(List.of(quizQuestionDto));
        doNothing().when(quizService).deleteQuiz(anyLong());
    }

    @Test
    public void testGetQuizById() throws Exception {
        mockMvc.perform(get("/quizzes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Sample Quiz"))
                .andExpect(jsonPath("$.description").value("This is a sample quiz"));
    }

    @Test
    public void testGetAllQuizzes() throws Exception {
        mockMvc.perform(get("/quizzes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Sample Quiz"))
                .andExpect(jsonPath("$[0].description").value("This is a sample quiz"));
    }

    @Test
    public void testCreateQuiz() throws Exception {
        mockMvc.perform(post("/quizzes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Sample Quiz\",\"description\":\"This is a sample quiz\",\"createdDate\":\"2023-01-01T00:00:00\",\"isActive\":true,\"dueDate\":\"2023-01-01\",\"reminderDate\":\"2023-01-01\",\"courseOrder\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Sample Quiz"))
                .andExpect(jsonPath("$.description").value("This is a sample quiz"));
    }

    @Test
    public void testUpdateQuiz() throws Exception {
        mockMvc.perform(put("/quizzes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Quiz\",\"description\":\"This is an updated quiz\",\"createdDate\":\"2023-01-01T00:00:00\",\"isActive\":true,\"dueDate\":\"2023-01-01\",\"reminderDate\":\"2023-01-01\",\"courseOrder\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Sample Quiz"))
                .andExpect(jsonPath("$.description").value("This is a sample quiz"));
    }

    @Test
    public void testDeleteQuiz() throws Exception {
        mockMvc.perform(delete("/quizzes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetQuizQuestions() throws Exception {
        mockMvc.perform(get("/quizzes/1/questions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].questionTitle").value("Sample Question"))
                .andExpect(jsonPath("$[0].manyAnswers").value(false));
    }
}