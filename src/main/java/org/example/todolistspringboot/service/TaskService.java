package org.example.todolistspringboot.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.todolistspringboot.dto.TaskDtoRequest;
import org.example.todolistspringboot.dto.TaskDtoResponse;
import org.example.todolistspringboot.entity.Task;
import org.example.todolistspringboot.exception.TaskNotFoundException;
import org.example.todolistspringboot.mapper.TaskMapper;
import org.example.todolistspringboot.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor

public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskDtoResponse createTask(@Valid TaskDtoRequest taskDtoRequest) {
        log.info("Добавление задачи в базу данных {}", taskDtoRequest);
        Task task = taskMapper.toEntity(taskDtoRequest);
        Task response = taskRepository.save(task);
        log.info("задача добавлена {}", response);
        return taskMapper.toDto(response);

    }

    public List<TaskDtoResponse> findAllTask() {
        log.info("Вывод всех задач из базы данных");
        List<Task> allTask = taskRepository.findAll();
        List<TaskDtoResponse> listTask = taskMapper.toList(allTask);
        log.info("все задачи {}", listTask);
        return listTask;

    }

    public TaskDtoResponse findById(Long id) {
        log.info("Вывод задачи по id{}",id);
        Task task = taskRepository.findById(id).orElseThrow(()
                -> new RuntimeException("not found id in bd" + id));
        log.info("Найдена задача с id {}: {}", id, task);
        return taskMapper.toDto(task);
    }

    public TaskDtoResponse updateEntity(Long id, TaskDtoRequest taskDtoRequest) {
        Task task = taskRepository.findById(id).orElseThrow(()
                -> new RuntimeException("not found id in bd" + id));
        taskMapper.update(taskDtoRequest, task);
        Task savedTask = taskRepository.save(task);
        log.info("Задача с id {} обновлена: {}", id, savedTask);

        return taskMapper.toDto(savedTask);
    }

    public void deleteById(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);

        }
        taskRepository.deleteById(id);
        log.info("Задача с id {} удалена", id);
    }

}
