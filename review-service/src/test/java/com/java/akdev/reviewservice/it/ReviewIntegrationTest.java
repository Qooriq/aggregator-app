package com.java.akdev.reviewservice.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.akdev.reviewservice.IntegrationTestBase;
import com.java.akdev.reviewservice.dto.ReviewCreateDto;
import com.java.akdev.reviewservice.dto.ReviewReadDto;
import com.java.akdev.reviewservice.enumeration.Order;
import com.java.akdev.reviewservice.enumeration.SortField;
import com.java.akdev.reviewservice.util.TestSetUps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
@ActiveProfiles("test")
public class ReviewIntegrationTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private Long id;
    private Integer page;
    private Integer size;
    private SortField sortField;
    private Order order;
    private ReviewReadDto reviewReadDto;
    private ReviewCreateDto reviewCreateDto;
    private ReviewReadDto reviewUpdateReadDto;

    @BeforeEach
    void setUp() {
        page = TestSetUps.DEFAULT_PAGE;
        size = TestSetUps.DEFAULT_PAGE_SIZE;
        sortField = TestSetUps.SORT_FIELD;
        order = TestSetUps.ORDER;
        id = TestSetUps.id;
        reviewReadDto = TestSetUps.getReadDto();
        reviewCreateDto = TestSetUps.getCreateDto();
        reviewUpdateReadDto = TestSetUps.getUpdateDto();
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/reviews")
                        .param("page", page.toString())
                        .param("size", size.toString())
                        .param("sortField", sortField.name())
                        .param("order", order.name()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").isNotEmpty());
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/reviews/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.review").value(reviewReadDto.review().toString()))
                .andExpect(jsonPath("$.comment").value(reviewReadDto.comment()))
                .andExpect(jsonPath("$.receiver").value(reviewReadDto.receiver().toString()));
    }

    @Test
    void findByIdNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/reviews/{id}", Integer.MAX_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void createReview() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reviewCreateDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.review").value(reviewUpdateReadDto.review().toString()))
                .andExpect(jsonPath("$.comment").value(reviewUpdateReadDto.comment()))
                .andExpect(jsonPath("$.receiver").value(reviewUpdateReadDto.receiver().toString()));
    }

    @Test
    void updateReview() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/reviews/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reviewCreateDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.review").value(reviewUpdateReadDto.review().toString()))
                .andExpect(jsonPath("$.comment").value(reviewUpdateReadDto.comment()))
                .andExpect(jsonPath("$.receiver").value(reviewUpdateReadDto.receiver().toString()));
    }

    @Test
    void deleteReview() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/reviews/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
