package com.softuni.volunteerplatform.causes.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softuni.volunteerplatform.causes.model.dtos.AddCauseDTO;
import com.softuni.volunteerplatform.causes.model.enums.Level;
import com.softuni.volunteerplatform.causes.repository.CauseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CauseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CauseRepository causeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {

        causeRepository.deleteAll();
    }

    @Test
    public void testGetAllCauses_ReturnEmptyList() throws Exception {
        mockMvc.perform(get("/causes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testCreateAndGetCause() throws Exception {

        AddCauseDTO addCauseDTO = new AddCauseDTO(
                "Integration Test Title",
                "Integration Test Description",
                Level.JUNIOR,
                "http://example.com/test.png",
                "Integration Author",
                LocalDateTime.now()
        );


        mockMvc.perform(post("/causes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addCauseDTO)))
                .andExpect(status().isOk());


        mockMvc.perform(get("/causes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testDeleteCause() throws Exception {

        AddCauseDTO addCauseDTO = new AddCauseDTO(
                "Title for Delete",
                "Description for Delete",
                Level.ADVANCED,

                "http://example.com/delete.png",
                "Delete Author",
                LocalDateTime.now()
        );

        mockMvc.perform(post("/causes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addCauseDTO)))
                .andExpect(status().isOk());


        Long causeId = causeRepository.findAll().get(0).getId();


        mockMvc.perform(delete("/causes/" + causeId))
                .andExpect(status().isOk());


        mockMvc.perform(get("/causes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
