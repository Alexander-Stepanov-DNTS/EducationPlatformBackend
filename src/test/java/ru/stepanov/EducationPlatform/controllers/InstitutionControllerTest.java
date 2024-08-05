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
import ru.stepanov.EducationPlatform.DTO.InstitutionDto;
import ru.stepanov.EducationPlatform.EducationPlatformApplication;
import ru.stepanov.EducationPlatform.services.impl.InstitutionServiceImpl;

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

@SpringBootTest(classes = {EducationPlatformApplication.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class InstitutionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InstitutionServiceImpl institutionService;

    private InstitutionDto institutionDto;

    @BeforeEach
    public void setup() {
        institutionDto = new InstitutionDto();
        institutionDto.setId(1L);
        institutionDto.setName("Institution Name");
        institutionDto.setType("University");

        when(institutionService.getInstitutionById(anyLong())).thenReturn(institutionDto);
        when(institutionService.createInstitution(any(InstitutionDto.class))).thenReturn(institutionDto);
        when(institutionService.updateInstitution(anyLong(), any(InstitutionDto.class))).thenReturn(institutionDto);
    }

    @Test
    public void testGetInstitutionById() throws Exception {
        mockMvc.perform(get("/institutions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Institution Name"))
                .andExpect(jsonPath("$.type").value("University"));
    }

    @Test
    public void testGetAllInstitutions() throws Exception {
        when(institutionService.getAllInstitutions()).thenReturn(Collections.singletonList(institutionDto));

        mockMvc.perform(get("/institutions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Institution Name"))
                .andExpect(jsonPath("$[0].type").value("University"));
    }

    @Test
    public void testCreateInstitution() throws Exception {
        mockMvc.perform(post("/institutions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Institution Name\",\"type\":\"University\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Institution Name"))
                .andExpect(jsonPath("$.type").value("University"));
    }

    @Test
    public void testUpdateInstitution() throws Exception {
        mockMvc.perform(put("/institutions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Institution Name\",\"type\":\"University\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Institution Name"))
                .andExpect(jsonPath("$.type").value("University"));
    }

    @Test
    public void testDeleteInstitution() throws Exception {
        mockMvc.perform(delete("/institutions/1"))
                .andExpect(status().isNoContent());
    }
}