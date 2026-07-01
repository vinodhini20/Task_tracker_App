package com.example.tasktracker.controller;

import com.example.tasktracker.model.Task;
import com.example.tasktracker.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void whenGetTaskById_andTaskExists_thenReturnTask() throws Exception {

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");

        when(taskService.findTaskById(1L))
                .thenReturn(Optional.of(task));

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Task"));
    }

    @Test
    public void whenGetTaskById_andTaskDoesNotExist_thenReturnNotFound() throws Exception {

        when(taskService.findTaskById(1L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenCreateTask_withValidData_thenReturnCreatedTask() throws Exception {

        Task taskToCreate = new Task();
        taskToCreate.setTitle("New Task");
        taskToCreate.setDueDate(Instant.now().plusSeconds(86400)); // Due tomorrow

        Task savedTask = new Task();
        savedTask.setId(1L);
        savedTask.setTitle("New Task");

        when(taskService.saveTask(any(Task.class), any()))
                .thenReturn(savedTask);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskToCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("New Task"));
    }

    @Test
    public void whenCreateTask_withInvalidData_thenReturnUnprocessableEntity() throws Exception {

        // Title is blank, which is invalid
        Task taskToCreate = new Task();
        taskToCreate.setTitle("");

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskToCreate)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void whenUpdateTask_andTaskExists_thenReturnUpdatedTask() throws Exception {

        Task taskDetails = new Task();
        taskDetails.setTitle("Updated Title");
        taskDetails.setStatus(Task.Status.DONE);
        taskDetails.setDueDate(Instant.now().plusSeconds(86400));

        when(taskService.updateTask(eq(1L), any(Task.class), any()))
                .thenReturn(taskDetails);

        mockMvc.perform(put("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.status").value("DONE"));
    }

    @Test
    public void whenDeleteTask_andTaskExists_thenReturnNoContent() throws Exception {

        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isNoContent());
    }
}