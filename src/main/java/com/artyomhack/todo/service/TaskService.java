package com.artyomhack.todo.service;

import com.artyomhack.todo.entity.TaskEntity;
import com.artyomhack.todo.model.task.TaskRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {
    TaskEntity create(TaskRequest request);
    TaskEntity update(Long id, TaskRequest request);
    List<TaskEntity> fetchAll();
    TaskEntity fetchById(Long id);
    boolean deleteById(Long id);
}
