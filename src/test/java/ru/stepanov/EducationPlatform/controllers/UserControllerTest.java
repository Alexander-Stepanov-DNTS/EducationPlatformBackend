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
import ru.stepanov.EducationPlatform.DTO.UserDto;
import ru.stepanov.EducationPlatform.services.UserService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

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
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserDto userDto;

    @BeforeEach
    public void setup() {
        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmailAddress("user@example.com");
        userDto.setPassword("password");
        userDto.setSignupDate(LocalDate.now());
        userDto.setLogin("userlogin");

        CourseDto courseDto = new CourseDto();
        courseDto.setId(1L);
        courseDto.setName("Course Name");
        courseDto.setDescription("Course Description");
        courseDto.setPrice(100L);
        courseDto.setIsProgressLimited(false);
        courseDto.setPicture_url("http://example.com/image.jpg");

        when(userService.getUserById(anyLong())).thenReturn(userDto);
        when(userService.getCoursesByUserId(anyLong())).thenReturn(List.of(courseDto));
        when(userService.createUser(any(UserDto.class))).thenReturn(userDto);
        when(userService.updateUser(anyLong(), any(UserDto.class))).thenReturn(userDto);
    }

    @Test
    public void testGetUserById() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.emailAddress").value("user@example.com"))
                .andExpect(jsonPath("$.login").value("userlogin"));
    }

    @Test
    public void testGetCoursesByUserId() throws Exception {
        mockMvc.perform(get("/users/1/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Course Name"))
                .andExpect(jsonPath("$[0].description").value("Course Description"))
                .andExpect(jsonPath("$[0].price").value(100L))
                .andExpect(jsonPath("$[0].isProgressLimited").value(false))
                .andExpect(jsonPath("$[0].picture_url").value("http://example.com/image.jpg"));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(Collections.singletonList(userDto));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].emailAddress").value("user@example.com"))
                .andExpect(jsonPath("$[0].login").value("userlogin"));
    }

    @Test
    public void testCreateUser() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"emailAddress\":\"user@example.com\",\"password\":\"password\",\"signupDate\":\"2023-01-01\",\"login\":\"userlogin\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.emailAddress").value("user@example.com"))
                .andExpect(jsonPath("$.login").value("userlogin"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"emailAddress\":\"updateduser@example.com\",\"password\":\"newpassword\",\"signupDate\":\"2023-01-01\",\"login\":\"updateduserlogin\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.emailAddress").value("user@example.com"))
                .andExpect(jsonPath("$.login").value("userlogin"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());
    }
}