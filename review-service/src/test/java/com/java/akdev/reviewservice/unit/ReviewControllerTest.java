package com.java.akdev.reviewservice.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.akdev.reviewservice.controller.ReviewController;
import com.java.akdev.reviewservice.dto.ReviewCreateDto;
import com.java.akdev.reviewservice.dto.ReviewReadDto;
import com.java.akdev.reviewservice.service.ReviewService;
import com.java.akdev.reviewservice.util.TestSetUps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ReviewService reviewService;

    private Long id;
    private ReviewCreateDto reviewCreateDto;
    private ReviewReadDto reviewReadDto;

    @BeforeEach
    void setUp() {
        id = TestSetUps.id;
        reviewCreateDto = TestSetUps.getCreateDto();
        reviewReadDto = TestSetUps.getReadDto();
    }

    @Test
    void getReviewById() throws Exception {
        when(reviewService.findById(id))
                .thenReturn(reviewReadDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/reviews/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.review").value(reviewReadDto.review().toString()))
                .andExpect(jsonPath("$.receiver").value(reviewReadDto.receiver().toString()))
                .andExpect(jsonPath("$.comment").value(reviewReadDto.comment()));
    }

    @Test
    void createReview() throws Exception {
        when(reviewService.createReview(reviewCreateDto))
                .thenReturn(reviewReadDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reviewCreateDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.review").value(reviewReadDto.review().toString()))
                .andExpect(jsonPath("$.receiver").value(reviewReadDto.receiver().toString()))
                .andExpect(jsonPath("$.comment").value(reviewReadDto.comment()));
    }

    @Test
    void updateReview() throws Exception {
        when(reviewService.update(id, reviewCreateDto))
                .thenReturn(reviewReadDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/reviews/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reviewCreateDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.review").value(reviewReadDto.review().toString()))
                .andExpect(jsonPath("$.receiver").value(reviewReadDto.receiver().toString()))
                .andExpect(jsonPath("$.comment").value(reviewReadDto.comment()));
    }

    @Test
    void deleteReview() throws Exception {
        doNothing().when(reviewService).delete(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/reviews/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
