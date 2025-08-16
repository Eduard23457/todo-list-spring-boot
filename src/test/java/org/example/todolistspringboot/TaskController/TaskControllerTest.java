package org.example.todolistspringboot.TaskController;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.todolistspringboot.controller.TaskController;
import org.example.todolistspringboot.dto.TaskDtoRequest;
import org.example.todolistspringboot.dto.TaskDtoResponse;
import org.example.todolistspringboot.entity.TaskStatus;
import org.example.todolistspringboot.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createTask_returnsTaskDto() throws Exception {
        TaskDtoRequest request = new TaskDtoRequest();
        request.setTitle("Test");
        request.setDescription("Desc");
        request.setStatus(TaskStatus.NEW);

        TaskDtoResponse response = new TaskDtoResponse();
        response.setTitle("Test");
        response.setDescription("Desc");
        response.setStatus(TaskStatus.NEW);

        when(taskService.createTask(any(TaskDtoRequest.class))).thenReturn(response);

        mockMvc.perform(post("/tasks/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test"))
                .andExpect(jsonPath("$.description").value("Desc"))
                .andExpect(jsonPath("$.status").value("NEW"));
    }

    @Test
    void getAllTask_returnsList() throws Exception {
        TaskDtoResponse response = new TaskDtoResponse();
        response.setTitle("Task1");
        response.setDescription("Desc1");
        response.setStatus(TaskStatus.NEW);

        when(taskService.findAllTask()).thenReturn(List.of(response));

        mockMvc.perform(get("/tasks/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Task1"));
    }

    @Test
    void findById_returnsTask() throws Exception {
        TaskDtoResponse response = new TaskDtoResponse();
        response.setTitle("Task1");
        response.setDescription("Desc1");
        response.setStatus(TaskStatus.NEW);

        when(taskService.findById(1L)).thenReturn(response);

        mockMvc.perform(get("/tasks/findById")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Task1"));
    }

    @Test
    void updateTask_returnsUpdatedTask() throws Exception {
        TaskDtoRequest request = new TaskDtoRequest();
        request.setTitle("Updated");
        request.setDescription("Desc Updated");
        request.setStatus(TaskStatus.IN_PROGRESS);

        TaskDtoResponse response = new TaskDtoResponse();
        response.setTitle("Updated");
        response.setDescription("Desc Updated");
        response.setStatus(TaskStatus.IN_PROGRESS);

        when(taskService.updateEntity(eq(1L), any(TaskDtoRequest.class))).thenReturn(response);

        mockMvc.perform(put("/tasks/update")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated"))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));
    }

    @Test
    void deleteById_returnsNotFound() throws Exception {
        doNothing().when(taskService).deleteById(1L);

        mockMvc.perform(delete("/tasks/delete")
                        .param("id", "1"))
                .andExpect(status().isNotFound());

        verify(taskService, times(1)).deleteById(1L);
    }
}