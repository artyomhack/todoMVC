package com.artyomhack.todo.service;

import com.artyomhack.todo.entity.TaskEntity;
import com.artyomhack.todo.model.task.TaskRequest;
import com.artyomhack.todo.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskEntity create(TaskRequest request) {
        return taskRepository.save(convertFromRequest(request));
    }

    @Override
    public TaskEntity update(Long id, TaskRequest request) {
        var data = taskRepository.findById(id).map(it -> {
            var title = it.getTitle();
            var description = it.getDescription();
            if (!title.equals(request.getTitle())) title = request.getTitle();
            if (!description.equals(request.getDescription())) description = request.getDescription();
            var entity = new TaskEntity(request.getId(), title, description);
            return taskRepository.save(entity);
        });
        return data.orElse(null);
    }

    @Override
    public List<TaskEntity> fetchAll() {
        return null;
    }

    @Override
    public TaskEntity fetchById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteById(Long id) {
        if (taskRepository.findById(id).isPresent()) {
            taskRepository.deleteById(id);
            return true;

        }
        return false;
    }

    private TaskEntity convertFromRequest(TaskRequest request) {
        return new TaskEntity(
                request.getId(),
                request.getTitle(),
                request.getDescription()
        );
    }
}
