package org.savea.todoapp.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.savea.todoapp.models.Status;
import org.savea.todoapp.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @WithMockUser
    void createUpdateAndFeed() throws Exception {
        Task t = new Task();
        t.setTitle("Test Task");
        t.setDescription("desc");

        MvcResult createRes = mvc.perform(post("/api/tasks")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(t)))
                .andExpect(status().isCreated())
                .andReturn();

        Task created = mapper.readValue(createRes.getResponse().getContentAsByteArray(), Task.class);
        assertThat(created.getId()).isNotNull();

        created.setTitle("Updated title");
        created.setStatus(Status.COMPLETED);

        MvcResult updateRes = mvc.perform(put("/api/tasks/" + created.getId())
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(created)))
                .andExpect(status().isOk())
                .andReturn();

        Task updated = mapper.readValue(updateRes.getResponse().getContentAsByteArray(), Task.class);
        assertThat(updated.getTitle()).isEqualTo("Updated title");
        assertThat(updated.getStatus()).isEqualTo(Status.COMPLETED);

        MvcResult feedRes = mvc.perform(get("/api/tasks/" + created.getId() + "/activity"))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode feed = mapper.readTree(feedRes.getResponse().getContentAsByteArray());
        assertThat(feed.isArray()).isTrue();
        assertThat(feed.size()).isGreaterThanOrEqualTo(1);

        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(feed));
    }
}
