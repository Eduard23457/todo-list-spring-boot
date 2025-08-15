package org.example.todolistspringboot.controller;


import lombok.RequiredArgsConstructor;
import org.example.todolistspringboot.dto.TaskDtoRequest;
import org.example.todolistspringboot.dto.TaskDtoResponse;
import org.example.todolistspringboot.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService; // final поле, Lombok создаст конструктор

    @PostMapping("/add")
    public ResponseEntity<TaskDtoResponse> createTask(@RequestBody TaskDtoRequest taskDtoRequest) {
        TaskDtoResponse response = taskService.createTask(taskDtoRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<TaskDtoResponse>> getAllTask() {
        List<TaskDtoResponse> allTask = taskService.findAllTask();
        return ResponseEntity.ok(allTask);
    }

    @GetMapping("/findById")
    public ResponseEntity<TaskDtoResponse> findById(@RequestParam Long id) {
        TaskDtoResponse task = taskService.findById(id);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/update")
    public ResponseEntity<TaskDtoResponse> updateTask(@RequestParam Long id,
                                                      @RequestBody TaskDtoRequest taskDtoRequest) {
        TaskDtoResponse taskDtoResponse = taskService.updateEntity(id, taskDtoRequest);
        return ResponseEntity.ok(taskDtoResponse);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Void>deleteById(@RequestParam Long id){
        taskService.deleteById(id);
        return ResponseEntity.notFound().build();
    }
}
