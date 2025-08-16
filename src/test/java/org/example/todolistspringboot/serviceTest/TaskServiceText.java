package org.example.todolistspringboot.serviceTest;

import jakarta.validation.ConstraintViolationException;
import org.example.todolistspringboot.dto.TaskDtoRequest;
import org.example.todolistspringboot.dto.TaskDtoResponse;
import org.example.todolistspringboot.entity.Task;
import org.example.todolistspringboot.entity.TaskStatus;
import org.example.todolistspringboot.mapper.TaskMapper;
import org.example.todolistspringboot.repository.TaskRepository;
import org.example.todolistspringboot.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceText {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskMapper taskMapper;
    @InjectMocks
    private TaskService taskService;

    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTask_shouldReturnResponse_whenInputIsValid() {
        TaskDtoRequest request = new TaskDtoRequest();
        request.setTitle("Test task");
        request.setDescription("Test Description");
        request.setStatus(TaskStatus.NEW);

        Task task = new Task();
        task.setTitle("Test task");
        task.setDescription("test Description");
        task.setStatus(TaskStatus.NEW);

        TaskDtoResponse response = new TaskDtoResponse();
        response.setTitle("Test task");
        response.setDescription("Test Description");
        response.setStatus(TaskStatus.NEW);

        when(taskMapper.toEntity(request)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toDto(task)).thenReturn(response);

        TaskDtoResponse result = taskService.createTask(request);
        assertNotNull(result);
        assertEquals("Test task", result.getTitle());
        assertEquals("Test Description", result.getDescription());
        assertEquals(TaskStatus.NEW, result.getStatus());

        verify(taskRepository, times(1)).save(task);

    }

    @Test
    void createTask_shouldThrowException_whenTitleIsBlank() {
        // DTO с пустым title
        TaskDtoRequest request = new TaskDtoRequest();
        request.setTitle(""); // пустое название
        request.setDescription("Test Description");
        request.setStatus(TaskStatus.NEW);

        // Вызов метода и проверка выбрасывания исключения
        assertThrows(ConstraintViolationException.class, () -> {
            taskService.createTask(request);
        });

        // Проверка, что save не вызывался
        verify(taskRepository, never()).save(any());
    }

    @Test
    void getTask_existingId_returnsTask() {

        TaskDtoRequest request = new TaskDtoRequest();
        request.setTitle("Test Task");
        request.setDescription("Test Description");
        request.setStatus(TaskStatus.NEW);

        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setStatus(TaskStatus.NEW);

        TaskDtoResponse response = new TaskDtoResponse();
        response.setTitle("Test Task");
        response.setDescription("Test Description");
        response.setStatus(TaskStatus.NEW);

        when(taskRepository.findAll()).thenReturn(List.of(task));
        when(taskMapper.toList(List.of(task))).thenReturn(List.of(response));

        List<TaskDtoResponse> result = taskService.findAllTask();

        assertNotNull(result);
        assertEquals("Test Task", result.get(0).getTitle());
        assertEquals("Test Description", result.get(0).getDescription());
        assertEquals(TaskStatus.NEW, result.get(0).getStatus());

        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void getById_existingId_returnsTask() {
        Long id = 1L;

        TaskDtoRequest taskDtoRequest = new TaskDtoRequest();
        taskDtoRequest.setTitle("Test Task");
        taskDtoRequest.setDescription("Test Description");
        taskDtoRequest.setStatus(TaskStatus.NEW);

        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setStatus(TaskStatus.NEW);

        TaskDtoResponse response = new TaskDtoResponse();
        response.setTitle("Test Task");
        response.setDescription("Test Description");
        response.setStatus(TaskStatus.NEW);

        when(taskRepository.findById(id)).thenReturn(Optional.of(task));
        when(taskMapper.toDto(task)).thenReturn(response);

        TaskDtoResponse result = taskService.findById(id);

        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
        assertEquals("Test Description", result.getDescription());
        assertEquals(TaskStatus.NEW, result.getStatus());

        verify(taskRepository, times(1)).findById(id);
    }

    @Test
    void update_returnsTask() {

        Long id = 1L;

        TaskDtoRequest request = new TaskDtoRequest();
        request.setTitle("Test Task");
        request.setDescription("Test Description");
        request.setStatus(TaskStatus.NEW);

        Task task = new Task();
        task.setTitle("Old Task");
        task.setDescription("Old Description");
        task.setStatus(TaskStatus.NEW);

        Task newTask = new Task();
        newTask.setTitle("Updated Task");
        newTask.setDescription("Updated Description");
        newTask.setStatus(TaskStatus.IN_PROGRESS);

        TaskDtoResponse response = new TaskDtoResponse();
        response.setTitle("Updated Task");
        response.setDescription("Updated Description");
        response.setStatus(TaskStatus.IN_PROGRESS);

        when(taskRepository.findById(id)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(newTask);
        when(taskMapper.toDto(newTask)).thenReturn(response);

        TaskDtoResponse result = taskService.updateEntity(id, request);

        assertNotNull(result);
        assertEquals("Updated Task", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(TaskStatus.IN_PROGRESS, result.getStatus());

        verify(taskMapper).update(request, task);
        verify(taskRepository).save(task);

    }

    @Test
    void deleteById_existingTask_deletesTask() {
        Long id = 1L;

        // Настраиваем мок репозитория
        when(taskRepository.existsById(id)).thenReturn(true);

        // Вызываем метод
        taskService.deleteById(id);

        // Проверяем, что репозиторий вызван
        verify(taskRepository).deleteById(id);
    }
}

