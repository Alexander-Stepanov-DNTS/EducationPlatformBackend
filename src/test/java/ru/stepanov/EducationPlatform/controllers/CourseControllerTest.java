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
import ru.stepanov.EducationPlatform.DTO.CourseDto;
import ru.stepanov.EducationPlatform.services.CourseService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    private CourseDto courseDto;

    @BeforeEach
    public void setup() {
        courseDto = new CourseDto();
        courseDto.setId(1L);
        courseDto.setName("Course Name");
        courseDto.setDescription("Course Description");
        courseDto.setPrice(100L);
        courseDto.setIsProgressLimited(false);
        courseDto.setPicture_url("http://example.com/picture.png");

        when(courseService.getCourseById(anyLong())).thenReturn(courseDto);
        when(courseService.createCourse(any(CourseDto.class))).thenReturn(courseDto);
        when(courseService.updateCourse(anyLong(), any(CourseDto.class))).thenReturn(courseDto);
    }

    @Test
    public void testGetCourseById() throws Exception {
        mockMvc.perform(get("/courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Course Name"))
                .andExpect(jsonPath("$.description").value("Course Description"))
                .andExpect(jsonPath("$.price").value(100L))
                .andExpect(jsonPath("$.isProgressLimited").value(false))
                .andExpect(jsonPath("$.picture_url").value("http://example.com/picture.png"));
    }

    @Test
    public void testGetAllCourses() throws Exception {
        when(courseService.getAllCourses()).thenReturn(Collections.singletonList(courseDto));
        Map<String, Object> response = new HashMap<>();
        response.put("courses", Collections.singletonList(courseDto));

        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courses[0].id").value(1L))
                .andExpect(jsonPath("$.courses[0].name").value("Course Name"))
                .andExpect(jsonPath("$.courses[0].description").value("Course Description"))
                .andExpect(jsonPath("$.courses[0].price").value(100L))
                .andExpect(jsonPath("$.courses[0].isProgressLimited").value(false))
                .andExpect(jsonPath("$.courses[0].picture_url").value("http://example.com/picture.png"));
    }

    @Test
    public void testCreateCourse() throws Exception {
        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Course Name\",\"description\":\"Course Description\",\"price\":100,\"isProgressLimited\":false,\"picture_url\":\"http://example.com/picture.png\",\"category\":null}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Course Name"))
                .andExpect(jsonPath("$.description").value("Course Description"))
                .andExpect(jsonPath("$.price").value(100L))
                .andExpect(jsonPath("$.isProgressLimited").value(false))
                .andExpect(jsonPath("$.picture_url").value("http://example.com/picture.png"));
    }

    @Test
    public void testUpdateCourse() throws Exception {
        mockMvc.perform(put("/courses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Updated Course Name\",\"description\":\"Updated Course Description\",\"price\":150,\"isProgressLimited\":true,\"picture_url\":\"http://example.com/updated_picture.png\",\"category\":null}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Course Name"))
                .andExpect(jsonPath("$.description").value("Course Description"))
                .andExpect(jsonPath("$.price").value(100L))
                .andExpect(jsonPath("$.isProgressLimited").value(false))
                .andExpect(jsonPath("$.picture_url").value("http://example.com/picture.png"));
    }

    @Test
    public void testDeleteCourse() throws Exception {
        mockMvc.perform(delete("/courses/1"))
                .andExpect(status().isNoContent());
    }
}