package com.artyomhack.todo.service.task;

import com.artyomhack.todo.entity.TaskEntity;
import com.artyomhack.todo.entity.UserEntity;
import com.artyomhack.todo.model.task.TaskData;
import com.artyomhack.todo.model.task.TaskItem;
import com.artyomhack.todo.model.task.TaskRequest;
import com.artyomhack.todo.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskData create(TaskRequest request) {
        return toData(taskRepository.save(convertFromRequest(request)));
    }

    @Override
    public TaskData update(Long id, TaskRequest request) {
        System.out.println("REQUEST:" + request.getTitle() + " " + request.getDescription());
        var data = taskRepository.findById(id).map(it -> {
            var title = it.getTitle();
            var description = it.getDescription();
            if (!title.equals(request.getTitle())) title = request.getTitle();
            if (!description.equals(request.getDescription())) description = request.getDescription();
            var entity = new TaskEntity(request.getId(), title, description, it.getUsers());
            return taskRepository.save(entity);
        }).orElse(null);
        //Catch exception.
        if (Objects.isNull(data)) throw new IllegalArgumentException();
        System.out.println("DATA: " + data.getTitle() + " " + data.getDescription());
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

    private TaskEntity convertFromRequest(TaskRequest request) {
        if (Objects.isNull(request.getUsers())) request.setUsers(new HashSet<>());
        return new TaskEntity(
                request.getId(),
                request.getTitle(),
                request.getDescription(),
                request.getUsers().stream().map(it -> new UserEntity(it.getId(), it.getEmail()))
                        .collect(Collectors.toSet())
        );
    }

    private TaskData toData(TaskEntity entity) {
        return new TaskData(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription()
        );
    }

    private TaskItem toItem(TaskEntity entity) {
        return new TaskItem(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription()
        );
    }
}
