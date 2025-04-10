package com.softuni.volunteerplatform.causes.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softuni.volunteerplatform.causes.model.entity.Cause;
import com.softuni.volunteerplatform.causes.model.enums.Level;
import com.softuni.volunteerplatform.causes.repository.CauseRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
public class CauseControllerIT {

    @Container
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.26")
            .withDatabaseName("testdb")
            .withUsername("user")
            .withPassword("password");

    @Autowired
    private CauseRepository causeRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        causeRepository.deleteAll();
    }

    @AfterEach
    public void tearDown() {
        causeRepository.deleteAll();
    }

    @Test
    public void testGetById() throws Exception {
        Cause testCause = createTestCause();

        mockMvc.perform(get("/causes/{id}", testCause.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(testCause.getId().intValue())))
                .andExpect(jsonPath("$.title", is(testCause.getTitle())))
                .andExpect(jsonPath("$.description", is(testCause.getDescription())))
                .andExpect(jsonPath("$.imageUrl", is(testCause.getImageUrl())))
                .andExpect(jsonPath("$.authorName", is(testCause.getAuthorName())));
    }

    @Test
    public void testCauseNotFound() throws Exception {
        mockMvc.perform(get("/causes/{id}", 9999)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateCause() throws Exception {
        String jsonContent = """
                {
                    "title": "New Cause Title",
                    "description": "New Cause Description",
                    "level": "JUNIOR",
                    "imageUrl": "http://example.com/newcause.png",
                    "authorName": "New Author",
                    "created": "2023-01-01T00:00:00"
                }
                """;

        MvcResult result = mockMvc.perform(post("/causes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andReturn();

        Optional<Cause> createdCauseOpt = causeRepository.findAll().stream().findFirst();
        Assertions.assertTrue(createdCauseOpt.isPresent());

        Cause createdCause = createdCauseOpt.get();
        Assertions.assertEquals("New Cause Title", createdCause.getTitle());
        Assertions.assertEquals("New Cause Description", createdCause.getDescription());
        Assertions.assertEquals("http://example.com/newcause.png", createdCause.getImageUrl());
        Assertions.assertEquals("New Author", createdCause.getAuthorName());
        Assertions.assertEquals(Level.JUNIOR, createdCause.getLevel());
    }

    @Test
    public void testDeleteCause() throws Exception {
        Cause testCause = createTestCause();

        mockMvc.perform(delete("/causes/{id}", testCause.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Optional<Cause> deleted = causeRepository.findById(testCause.getId());
        Assertions.assertTrue(deleted.isEmpty());
    }

    private Cause createTestCause() {
        LocalDateTime now = LocalDateTime.now();
        Cause cause = new Cause()
                .setTitle("Test Cause")
                .setDescription("Test Cause Description")
                .setLevel(Level.JUNIOR)
                .setImageUrl("http://example.com/testcause.png")
                .setAuthorName("Test Author")
                .setCreated(now);
        return causeRepository.save(cause);
    }
}
