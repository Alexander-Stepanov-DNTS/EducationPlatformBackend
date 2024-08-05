package ru.stepanov.EducationPlatform.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.stepanov.EducationPlatform.DTO.AchievementDto;
import ru.stepanov.EducationPlatform.DTO.UserAchievementDto;
import ru.stepanov.EducationPlatform.DTO.UserDto;
import ru.stepanov.EducationPlatform.services.UserAchievementService;

import java.time.LocalDate;
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
public class UserAchievementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserAchievementService userAchievementService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserAchievementDto userAchievementDto;

    @BeforeEach
    public void setup() {
        objectMapper.findAndRegisterModules();

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmailAddress("student@example.com");
        userDto.setLogin("Student");
        userDto.setPassword("Password");

        AchievementDto achievementDto = new AchievementDto();
        achievementDto.setId(1L);
        achievementDto.setTitle("Achievement");

        userAchievementDto = new UserAchievementDto();
        userAchievementDto.setStudent(userDto);
        userAchievementDto.setAchievement(achievementDto);
        userAchievementDto.setDateAchieved(LocalDate.now());

        when(userAchievementService.getUserAchievementById(anyLong(), anyLong())).thenReturn(userAchievementDto);
        when(userAchievementService.getUserAchievementByUserId(anyLong())).thenReturn(Collections.singletonList(userAchievementDto));
        when(userAchievementService.getAllUserAchievements()).thenReturn(Collections.singletonList(userAchievementDto));
        when(userAchievementService.createUserAchievement(any(UserAchievementDto.class))).thenReturn(userAchievementDto);
        when(userAchievementService.updateUserAchievement(anyLong(), anyLong(), any(UserAchievementDto.class))).thenReturn(userAchievementDto);
        doNothing().when(userAchievementService).deleteUserAchievement(anyLong(), anyLong());
    }

    @Test
    public void testGetUserAchievementById() throws Exception {
        mockMvc.perform(get("/user-achievements/1/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.student.id").value(1L))
                .andExpect(jsonPath("$.achievement.id").value(1L))
                .andExpect(jsonPath("$.dateAchieved").value(LocalDate.now().toString()));
    }

    @Test
    public void testGetUserAchievementByUserId() throws Exception {
        mockMvc.perform(get("/user-achievements/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].student.id").value(1L))
                .andExpect(jsonPath("$[0].achievement.id").value(1L))
                .andExpect(jsonPath("$[0].dateAchieved").value(LocalDate.now().toString()));
    }

    @Test
    public void testGetAllUserAchievements() throws Exception {
        mockMvc.perform(get("/user-achievements"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].student.id").value(1L))
                .andExpect(jsonPath("$[0].achievement.id").value(1L))
                .andExpect(jsonPath("$[0].dateAchieved").value(LocalDate.now().toString()));
    }

    @Test
    public void testCreateUserAchievement() throws Exception {
        mockMvc.perform(post("/user-achievements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userAchievementDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.student.id").value(1L))
                .andExpect(jsonPath("$.achievement.id").value(1L))
                .andExpect(jsonPath("$.dateAchieved").value(LocalDate.now().toString()));
    }

    @Test
    public void testUpdateUserAchievement() throws Exception {
        mockMvc.perform(put("/user-achievements/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userAchievementDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.student.id").value(1L))
                .andExpect(jsonPath("$.achievement.id").value(1L))
                .andExpect(jsonPath("$.dateAchieved").value(LocalDate.now().toString()));
    }

    @Test
    public void testDeleteUserAchievement() throws Exception {
        mockMvc.perform(delete("/user-achievements/1/1"))
                .andExpect(status().isNoContent());
    }
}