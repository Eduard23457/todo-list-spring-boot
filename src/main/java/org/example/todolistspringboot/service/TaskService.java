package org.example.todolistspringboot.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.todolistspringboot.dto.TaskDtoRequest;
import org.example.todolistspringboot.dto.TaskDtoResponse;
import org.example.todolistspringboot.entity.Task;
import org.example.todolistspringboot.exception.TaskNotFoundException;
import org.example.todolistspringboot.mapper.TaskMapper;
import org.example.todolistspringboot.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor

public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskDtoResponse createTask(@Valid TaskDtoRequest taskDtoRequest) {
        Task task = taskMapper.toEntity(taskDtoRequest);
        Task response = taskRepository.save(task);
        return taskMapper.toDto(response);

    }

    public List<TaskDtoResponse> findAllTask() {
        List<Task> allTask = taskRepository.findAll();
        List<TaskDtoResponse> listTask = taskMapper.toList(allTask);
        return listTask;

    }

    public TaskDtoResponse findById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(()
                -> new RuntimeException("not found id in bd" + id));
        return taskMapper.toDto(task);
    }

    public TaskDtoResponse updateEntity(Long id, TaskDtoRequest taskDtoRequest) {
        Task task = taskRepository.findById(id).orElseThrow(()
                -> new RuntimeException("not found id in bd" + id));
        taskMapper.update(taskDtoRequest, task);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toDto(savedTask);
    }

    public void deleteById(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        taskRepository.deleteById(id);
    }

}
