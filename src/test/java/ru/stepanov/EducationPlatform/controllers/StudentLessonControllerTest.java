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
import ru.stepanov.EducationPlatform.DTO.StudentLessonDto;
import ru.stepanov.EducationPlatform.services.impl.StudentLessonServiceImpl;

import java.time.LocalDateTime;
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
public class StudentLessonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentLessonServiceImpl studentLessonService;

    @BeforeEach
    public void setup() {
        StudentLessonDto studentLessonDto = new StudentLessonDto();
        studentLessonDto.setCompletedDatetime(LocalDateTime.now());

        when(studentLessonService.getStudentLessonById(anyLong(), anyLong())).thenReturn(studentLessonDto);
        when(studentLessonService.getAllStudentLessons()).thenReturn(List.of(studentLessonDto));
        when(studentLessonService.createStudentLesson(any(StudentLessonDto.class))).thenReturn(studentLessonDto);
        when(studentLessonService.updateStudentLesson(anyLong(), anyLong(), any(StudentLessonDto.class))).thenReturn(studentLessonDto);
        doNothing().when(studentLessonService).deleteStudentLesson(anyLong(), anyLong());
    }

    @Test
    public void testGetStudentLessonById() throws Exception {
        mockMvc.perform(get("/student-lessons/1/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completedDatetime").isNotEmpty());
    }

    @Test
    public void testGetAllStudentLessons() throws Exception {
        mockMvc.perform(get("/student-lessons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].completedDatetime").isNotEmpty());
    }

    @Test
    public void testCreateStudentLesson() throws Exception {
        mockMvc.perform(post("/student-lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"completedDatetime\":\"2023-01-01T00:00:00\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completedDatetime").isNotEmpty());
    }

    @Test
    public void testUpdateStudentLesson() throws Exception {
        mockMvc.perform(put("/student-lessons/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"completedDatetime\":\"2023-01-01T00:00:00\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completedDatetime").isNotEmpty());
    }

    @Test
    public void testDeleteStudentLesson() throws Exception {
        mockMvc.perform(delete("/student-lessons/1/1"))
                .andExpect(status().isNoContent());
    }
}