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
import ru.stepanov.EducationPlatform.DTO.AchievementDto;
import ru.stepanov.EducationPlatform.services.AchievementService;

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
public class AchievementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AchievementService achievementService;

    @BeforeEach
    public void setup() {
        AchievementDto achievementDto = new AchievementDto();
        achievementDto.setId(1L);
        achievementDto.setTitle("Achievement Name");
        achievementDto.setDescription("Achievement Description");

        when(achievementService.getAchievementById(anyLong())).thenReturn(achievementDto);
        when(achievementService.createAchievement(any(AchievementDto.class))).thenReturn(achievementDto);
        when(achievementService.updateAchievement(anyLong(), any(AchievementDto.class))).thenReturn(achievementDto);
    }

    @Test
    public void testGetAchievementById() throws Exception {
        mockMvc.perform(get("/achievements/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Achievement Name"))
                .andExpect(jsonPath("$.description").value("Achievement Description"));
    }

    @Test
    public void testCreateAchievement() throws Exception {
        mockMvc.perform(post("/achievements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Achievement Name\",\"description\":\"Achievement Description\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Achievement Name"))
                .andExpect(jsonPath("$.description").value("Achievement Description"));
    }

    @Test
    public void testUpdateAchievement() throws Exception {
        mockMvc.perform(put("/achievements/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"title\":\"Updated Achievement Name\",\"description\":\"Updated Achievement Description\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Achievement Name"))
                .andExpect(jsonPath("$.description").value("Achievement Description"));
    }

    @Test
    public void testDeleteAchievement() throws Exception {
        mockMvc.perform(delete("/achievements/1"))
                .andExpect(status().isNoContent());
    }
}