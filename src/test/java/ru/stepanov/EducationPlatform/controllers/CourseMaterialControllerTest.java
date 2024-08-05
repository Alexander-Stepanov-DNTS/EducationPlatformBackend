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
import ru.stepanov.EducationPlatform.DTO.CourseMaterialDto;
import ru.stepanov.EducationPlatform.services.CourseMaterialService;

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
public class CourseMaterialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseMaterialService courseMaterialService;

    private CourseMaterialDto courseMaterialDto;

    @BeforeEach
    public void setup() {
        courseMaterialDto = new CourseMaterialDto();
        courseMaterialDto.setId(1L);
        courseMaterialDto.setMaterialTitle("Material Title");
        courseMaterialDto.setMaterialUrl("http://example.com/material.pdf");

        when(courseMaterialService.getCourseMaterialById(anyLong())).thenReturn(courseMaterialDto);
        when(courseMaterialService.createCourseMaterial(any(CourseMaterialDto.class))).thenReturn(courseMaterialDto);
        when(courseMaterialService.updateCourseMaterial(anyLong(), any(CourseMaterialDto.class))).thenReturn(courseMaterialDto);
    }

    @Test
    public void testGetCourseMaterialById() throws Exception {
        mockMvc.perform(get("/course-materials/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.materialTitle").value("Material Title"))
                .andExpect(jsonPath("$.materialUrl").value("http://example.com/material.pdf"));
    }

    @Test
    public void testGetAllMaterialsByCourse() throws Exception {
        when(courseMaterialService.getAllMaterialsByCourse(anyLong())).thenReturn(Collections.singletonList(courseMaterialDto));

        mockMvc.perform(get("/course-materials/course/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].materialTitle").value("Material Title"))
                .andExpect(jsonPath("$[0].materialUrl").value("http://example.com/material.pdf"));
    }

    @Test
    public void testGetAllMaterialsByUser() throws Exception {
        when(courseMaterialService.getAllMaterialsByUser(anyLong())).thenReturn(Collections.singletonList(courseMaterialDto));

        mockMvc.perform(get("/course-materials/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].materialTitle").value("Material Title"))
                .andExpect(jsonPath("$[0].materialUrl").value("http://example.com/material.pdf"));
    }

    @Test
    public void testGetAllCourseMaterials() throws Exception {
        when(courseMaterialService.getAllCourseMaterials()).thenReturn(Collections.singletonList(courseMaterialDto));

        mockMvc.perform(get("/course-materials"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].materialTitle").value("Material Title"))
                .andExpect(jsonPath("$[0].materialUrl").value("http://example.com/material.pdf"));
    }

    @Test
    public void testCreateCourseMaterial() throws Exception {
        mockMvc.perform(post("/course-materials")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"materialTitle\":\"Material Title\",\"materialUrl\":\"http://example.com/material.pdf\",\"course\":null}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.materialTitle").value("Material Title"))
                .andExpect(jsonPath("$.materialUrl").value("http://example.com/material.pdf"));
    }

    @Test
    public void testUpdateCourseMaterial() throws Exception {
        mockMvc.perform(put("/course-materials/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"materialTitle\":\"Updated Material Title\",\"materialUrl\":\"http://example.com/updated_material.pdf\",\"course\":null}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.materialTitle").value("Material Title"))
                .andExpect(jsonPath("$.materialUrl").value("http://example.com/material.pdf"));
    }

    @Test
    public void testDeleteCourseMaterial() throws Exception {
        mockMvc.perform(delete("/course-materials/1"))
                .andExpect(status().isNoContent());
    }
}