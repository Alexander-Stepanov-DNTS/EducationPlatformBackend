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
import ru.stepanov.EducationPlatform.DTO.QuizAnswerDto;
import ru.stepanov.EducationPlatform.services.impl.QuizAnswerServiceImpl;

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
public class QuizAnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuizAnswerServiceImpl quizAnswerService;

    @BeforeEach
    public void setup() {
        QuizAnswerDto quizAnswerDto = new QuizAnswerDto();
        quizAnswerDto.setId(1L);
        quizAnswerDto.setAnswerText("Sample Answer");
        quizAnswerDto.setIsCorrect(true);

        when(quizAnswerService.getQuizAnswerById(anyLong())).thenReturn(quizAnswerDto);
        when(quizAnswerService.getAllQuizAnswers()).thenReturn(List.of(quizAnswerDto));
        when(quizAnswerService.createQuizAnswer(any(QuizAnswerDto.class))).thenReturn(quizAnswerDto);
        when(quizAnswerService.updateQuizAnswer(anyLong(), any(QuizAnswerDto.class))).thenReturn(quizAnswerDto);
        doNothing().when(quizAnswerService).deleteQuizAnswer(anyLong());
    }

    @Test
    public void testGetQuizAnswerById() throws Exception {
        mockMvc.perform(get("/quiz-answers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.answerText").value("Sample Answer"))
                .andExpect(jsonPath("$.isCorrect").value(true));
    }

    @Test
    public void testGetAllQuizAnswers() throws Exception {
        mockMvc.perform(get("/quiz-answers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].answerText").value("Sample Answer"))
                .andExpect(jsonPath("$[0].isCorrect").value(true));
    }

    @Test
    public void testCreateQuizAnswer() throws Exception {
        mockMvc.perform(post("/quiz-answers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"answerText\":\"Sample Answer\",\"isCorrect\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.answerText").value("Sample Answer"))
                .andExpect(jsonPath("$.isCorrect").value(true));
    }

    @Test
    public void testUpdateQuizAnswer() throws Exception {
        mockMvc.perform(put("/quiz-answers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"answerText\":\"Updated Answer\",\"isCorrect\":false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.answerText").value("Sample Answer"))
                .andExpect(jsonPath("$.isCorrect").value(true));
    }

    @Test
    public void testDeleteQuizAnswer() throws Exception {
        mockMvc.perform(delete("/quiz-answers/1"))
                .andExpect(status().isNoContent());
    }
}