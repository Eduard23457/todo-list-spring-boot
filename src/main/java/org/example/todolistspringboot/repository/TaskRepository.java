package org.example.todolistspringboot.repository;

import org.example.todolistspringboot.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
