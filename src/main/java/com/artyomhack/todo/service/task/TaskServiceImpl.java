package com.artyomhack.todo.service.task;

import com.artyomhack.todo.entity.TaskEntity;
import com.artyomhack.todo.model.task.TaskData;
import com.artyomhack.todo.model.task.TaskItem;
import com.artyomhack.todo.model.task.TaskRequest;
import com.artyomhack.todo.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskData create(TaskRequest request) {
        return toData(taskRepository.save(TaskEntity.of(request)));
    }

    @Override
    public TaskData update(Long id, TaskRequest request) {
        var data = taskRepository.findById(id).map(it -> {
            var title = Optional.of(request.getTitle()).orElse(it.getTitle());
            var description = Optional.of(request.getDescription()).orElse(it.getDescription());
            var date = Optional.of(request.getDate()).orElse(it.getDate());
            return taskRepository.save(new TaskEntity(id, title, description, date, it.getUsers()));
        }).orElse(null);
        //Catch exception.
        if (Objects.isNull(data)) throw new IllegalArgumentException();
        return toData(data);
    }

    @Override
    public List<TaskItem> fetchAll() {
        return StreamSupport.stream(taskRepository.findAll().spliterator(), false)
                .filter(Objects::nonNull)
                .map(this::toItem)
                .toList();
    }

    @Override
    public TaskData fetchById(Long id) {
        var entity = taskRepository.findById(id).orElse(null);
        if (Objects.isNull(entity)) throw new IllegalArgumentException();
        return toData(entity);
    }

    @Override
    public boolean deleteById(Long id) {
        if (taskRepository.findById(id).isPresent()) {
            taskRepository.deleteById(id);
            return true;

        }
        return false;
    }

    private TaskData toData(TaskEntity entity) {
        return new TaskData(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getDate()
        );
    }

    private TaskItem toItem(TaskEntity entity) {
        return new TaskItem(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getDate()
        );
    }
}
