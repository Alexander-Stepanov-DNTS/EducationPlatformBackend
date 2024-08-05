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
import ru.stepanov.EducationPlatform.DTO.QuizQuestionDto;
import ru.stepanov.EducationPlatform.services.QuizQuestionService;

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
public class QuizQuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuizQuestionService quizQuestionService;

    @BeforeEach
    public void setup() {
        QuizQuestionDto quizQuestionDto = new QuizQuestionDto();
        quizQuestionDto.setId(1L);
        quizQuestionDto.setQuestionTitle("Sample Question");
        quizQuestionDto.setManyAnswers(false);

        // Mock the service responses
        when(quizQuestionService.getQuizQuestionById(anyLong())).thenReturn(quizQuestionDto);
        when(quizQuestionService.getAllQuizQuestions()).thenReturn(List.of(quizQuestionDto));
        when(quizQuestionService.createQuizQuestion(any(QuizQuestionDto.class))).thenReturn(quizQuestionDto);
        when(quizQuestionService.updateQuizQuestion(anyLong(), any(QuizQuestionDto.class))).thenReturn(quizQuestionDto);
        doNothing().when(quizQuestionService).deleteQuizQuestion(anyLong());
    }

    @Test
    public void testGetQuizQuestionById() throws Exception {
        mockMvc.perform(get("/quiz-questions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.questionTitle").value("Sample Question"));
    }

    @Test
    public void testGetAllQuizQuestions() throws Exception {
        mockMvc.perform(get("/quiz-questions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].questionTitle").value("Sample Question"));
    }

    @Test
    public void testCreateQuizQuestion() throws Exception {
        mockMvc.perform(post("/quiz-questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"questionTitle\":\"Sample Question\",\"manyAnswers\":false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.questionTitle").value("Sample Question"));
    }

    @Test
    public void testUpdateQuizQuestion() throws Exception {
        mockMvc.perform(put("/quiz-questions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"questionTitle\":\"Updated Question\",\"manyAnswers\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.questionTitle").value("Sample Question"));
    }

    @Test
    public void testDeleteQuizQuestion() throws Exception {
        mockMvc.perform(delete("/quiz-questions/1"))
                .andExpect(status().isNoContent());
    }
}