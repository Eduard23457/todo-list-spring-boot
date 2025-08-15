package org.example.todolistspringboot.mapper;


import org.example.todolistspringboot.dto.TaskDtoRequest;
import org.example.todolistspringboot.dto.TaskDtoResponse;
import org.example.todolistspringboot.entity.Task;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {


    Task toEntity(TaskDtoRequest taskDtoRequest);

    TaskDtoResponse toDto(Task task);

    List<TaskDtoResponse>toList(List<Task>tasks);
}
