package com.artyomhack.todo.service.task;

import com.artyomhack.todo.entity.TaskEntity;
import com.artyomhack.todo.model.task.TaskData;
import com.artyomhack.todo.model.task.TaskItem;
import com.artyomhack.todo.model.task.TaskRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {
    TaskData create(TaskRequest request);
    TaskData update(Long id, TaskRequest request);
    List<TaskItem> fetchAll();
    TaskData fetchById(Long id);
    boolean deleteById(Long id);
}
