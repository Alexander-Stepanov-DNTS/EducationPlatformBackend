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
import ru.stepanov.EducationPlatform.DTO.DirectionDto;
import ru.stepanov.EducationPlatform.services.DirectionService;

import java.util.Collections;

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
public class DirectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DirectionService directionService;

    private DirectionDto directionDto;

    @BeforeEach
    public void setUp() {
        directionDto = new DirectionDto();
        directionDto.setId(1L);
        directionDto.setName("Direction Name");
        directionDto.setDescription("Direction Description");
    }

    @Test
    public void testGetDirectionById() throws Exception {
        when(directionService.getDirectionById(anyLong())).thenReturn(directionDto);

        mockMvc.perform(get("/directions/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Direction Name"))
                .andExpect(jsonPath("$.description").value("Direction Description"));
    }

    @Test
    public void testGetAllDirections() throws Exception {
        when(directionService.getAllDirections()).thenReturn(Collections.singletonList(directionDto));

        mockMvc.perform(get("/directions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Direction Name"))
                .andExpect(jsonPath("$[0].description").value("Direction Description"));
    }

    @Test
    public void testCreateDirection() throws Exception {
        when(directionService.createDirection(any(DirectionDto.class))).thenReturn(directionDto);

        mockMvc.perform(post("/directions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Direction Name\",\"description\":\"Direction Description\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Direction Name"))
                .andExpect(jsonPath("$.description").value("Direction Description"));
    }

    @Test
    public void testUpdateDirection() throws Exception {
        when(directionService.updateDirection(anyLong(), any(DirectionDto.class))).thenReturn(directionDto);

        mockMvc.perform(put("/directions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Direction Name\",\"description\":\"Updated Direction Description\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Direction Name"))
                .andExpect(jsonPath("$.description").value("Direction Description"));
    }

    @Test
    public void testDeleteDirection() throws Exception {
        mockMvc.perform(delete("/directions/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}