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
import ru.stepanov.EducationPlatform.DTO.LessonDto;
import ru.stepanov.EducationPlatform.services.impl.LessonServiceImpl;

import java.util.Collections;

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
public class LessonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LessonServiceImpl lessonService;

    @BeforeEach
    public void setup() {
        LessonDto lessonDto = new LessonDto();
        lessonDto.setId(1L);
        lessonDto.setName("Lesson 1");
        lessonDto.setNumber(1);
        lessonDto.setVideoUrl("http://example.com/video");
        lessonDto.setLessonDetails("Details about Lesson 1");
        lessonDto.setCourseOrder(1);

        when(lessonService.getLessonById(anyLong())).thenReturn(lessonDto);
        when(lessonService.getAllLessons()).thenReturn(Collections.singletonList(lessonDto));
        when(lessonService.createLesson(any(LessonDto.class))).thenReturn(lessonDto);
        when(lessonService.updateLesson(anyLong(), any(LessonDto.class))).thenReturn(lessonDto);
        doNothing().when(lessonService).deleteLesson(anyLong());
        when(lessonService.getLessonsFromCourse(anyLong())).thenReturn(Collections.singletonList(lessonDto));
    }

    @Test
    public void testGetLessonById() throws Exception {
        mockMvc.perform(get("/lessons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Lesson 1"));
    }

    @Test
    public void testGetAllLessons() throws Exception {
        mockMvc.perform(get("/lessons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Lesson 1"));
    }

    @Test
    public void testCreateLesson() throws Exception {
        mockMvc.perform(post("/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Lesson 1\",\"number\":1,\"videoUrl\":\"http://example.com/video\",\"lessonDetails\":\"Details about Lesson 1\",\"courseOrder\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Lesson 1"));
    }

    @Test
    public void testUpdateLesson() throws Exception {
        mockMvc.perform(put("/lessons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Lesson\",\"number\":1,\"videoUrl\":\"http://example.com/video\",\"lessonDetails\":\"Updated details\",\"courseOrder\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Lesson 1"));
    }

    @Test
    public void testDeleteLesson() throws Exception {
        mockMvc.perform(delete("/lessons/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetLessonsFromCourse() throws Exception {
        mockMvc.perform(get("/lessons/course/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Lesson 1"));
    }
}