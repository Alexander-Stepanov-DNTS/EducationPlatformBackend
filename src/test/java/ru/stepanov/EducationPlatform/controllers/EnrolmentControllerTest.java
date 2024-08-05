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
import ru.stepanov.EducationPlatform.DTO.EnrolmentDto;
import ru.stepanov.EducationPlatform.DTO.UserDto;
import ru.stepanov.EducationPlatform.services.EnrolmentService;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EnrolmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnrolmentService enrolmentService;

    private EnrolmentDto enrolmentDto;

    @BeforeEach
    public void setup() {
        CourseDto courseDto = new CourseDto();
        courseDto.setId(1L);
        courseDto.setName("Course Name");
        courseDto.setDescription("Course Description");
        courseDto.setPrice(100L);
        courseDto.setIsProgressLimited(false);
        courseDto.setPicture_url("http://example.com/image.jpg");

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmailAddress("user@example.com");
        userDto.setPassword("password");
        userDto.setSignupDate(LocalDateTime.now().toLocalDate());
        userDto.setLogin("userlogin");

        enrolmentDto = new EnrolmentDto();
        enrolmentDto.setCourse(courseDto);
        enrolmentDto.setStudent(userDto);
        enrolmentDto.setEnrolmentDatetime(LocalDateTime.now());
        enrolmentDto.setIsAuthor(false);

        EnrolmentDto updatedEnrolmentDto = new EnrolmentDto();
        updatedEnrolmentDto.setCourse(courseDto);
        updatedEnrolmentDto.setStudent(userDto);
        updatedEnrolmentDto.setEnrolmentDatetime(LocalDateTime.now());
        updatedEnrolmentDto.setIsAuthor(true);

        when(enrolmentService.getEnrolmentById(anyLong(), anyLong())).thenReturn(enrolmentDto);
        when(enrolmentService.createEnrolment(any(EnrolmentDto.class))).thenReturn(enrolmentDto);
        when(enrolmentService.updateEnrolment(anyLong(), anyLong(), any(EnrolmentDto.class))).thenReturn(updatedEnrolmentDto);
        when(enrolmentService.isEnrolled(anyLong(), anyLong())).thenReturn(true);
    }

    @Test
    public void testGetEnrolmentById() throws Exception {
        mockMvc.perform(get("/enrolments/1/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.course.id").value(1L))
                .andExpect(jsonPath("$.student.id").value(1L))
                .andExpect(jsonPath("$.isAuthor").value(false));
    }

    @Test
    public void testGetAllEnrolments() throws Exception {
        when(enrolmentService.getAllEnrolments()).thenReturn(Collections.singletonList(enrolmentDto));

        mockMvc.perform(get("/enrolments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].course.id").value(1L))
                .andExpect(jsonPath("$[0].student.id").value(1L))
                .andExpect(jsonPath("$[0].isAuthor").value(false));
    }

    @Test
    public void testCreateEnrolment() throws Exception {
        mockMvc.perform(post("/enrolments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"course\":{\"id\":1},\"student\":{\"id\":1},\"isAuthor\":false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.course.id").value(1L))
                .andExpect(jsonPath("$.student.id").value(1L))
                .andExpect(jsonPath("$.isAuthor").value(false));
    }

    @Test
    public void testUpdateEnrolment() throws Exception {
        mockMvc.perform(put("/enrolments/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"course\":{\"id\":1},\"student\":{\"id\":1},\"isAuthor\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.course.id").value(1L))
                .andExpect(jsonPath("$.student.id").value(1L))
                .andExpect(jsonPath("$.isAuthor").value(true));
    }

    @Test
    public void testDeleteEnrolment() throws Exception {
        mockMvc.perform(delete("/enrolments/1/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testIsEnrolled() throws Exception {
        mockMvc.perform(get("/enrolments/isEnrolled")
                        .param("courseId", "1")
                        .param("studentId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}