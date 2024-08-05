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
import ru.stepanov.EducationPlatform.DTO.ReviewDto;
import ru.stepanov.EducationPlatform.services.impl.ReviewServiceImpl;

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
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewServiceImpl reviewService;

    @BeforeEach
    public void setup() {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(1L);
        reviewDto.setCreatedDate(LocalDateTime.now());
        reviewDto.setScore(5);
        reviewDto.setReviewText("Great course!");

        when(reviewService.getReviewById(anyLong())).thenReturn(reviewDto);
        when(reviewService.getAllReviewsByCourse(anyLong())).thenReturn(List.of(reviewDto));
        when(reviewService.getAllReviews()).thenReturn(List.of(reviewDto));
        when(reviewService.createReview(any(ReviewDto.class))).thenReturn(reviewDto);
        when(reviewService.updateReview(anyLong(), any(ReviewDto.class))).thenReturn(reviewDto);
        doNothing().when(reviewService).deleteReview(anyLong());
    }

    @Test
    public void testGetReviewById() throws Exception {
        mockMvc.perform(get("/reviews/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.score").value(5))
                .andExpect(jsonPath("$.reviewText").value("Great course!"));
    }

    @Test
    public void testGetAllReviewsByCourse() throws Exception {
        mockMvc.perform(get("/reviews/course/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].score").value(5))
                .andExpect(jsonPath("$[0].reviewText").value("Great course!"));
    }

    @Test
    public void testGetAllReviews() throws Exception {
        mockMvc.perform(get("/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].score").value(5))
                .andExpect(jsonPath("$[0].reviewText").value("Great course!"));
    }

    @Test
    public void testCreateReview() throws Exception {
        mockMvc.perform(post("/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"score\":5,\"reviewText\":\"Great course!\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.score").value(5))
                .andExpect(jsonPath("$.reviewText").value("Great course!"));
    }

    @Test
    public void testUpdateReview() throws Exception {
        mockMvc.perform(put("/reviews/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"score\":4,\"reviewText\":\"Good course!\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.score").value(5))
                .andExpect(jsonPath("$.reviewText").value("Great course!"));
    }

    @Test
    public void testDeleteReview() throws Exception {
        mockMvc.perform(delete("/reviews/1"))
                .andExpect(status().isNoContent());
    }
}