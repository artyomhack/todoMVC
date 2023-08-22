package com.artyomhack.todo.service.user;

import com.artyomhack.todo.entity.TaskEntity;
import com.artyomhack.todo.entity.UserEntity;
import com.artyomhack.todo.model.task.TaskData;
import com.artyomhack.todo.model.task.TaskItem;
import com.artyomhack.todo.model.task.TaskRequest;
import com.artyomhack.todo.model.user.UserData;
import com.artyomhack.todo.model.user.UserItem;
import com.artyomhack.todo.model.user.UserRequest;
import com.artyomhack.todo.repository.TaskRepository;
import com.artyomhack.todo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public UserServiceImpl(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public UserData create(UserRequest request) {
        return toData(userRepository.save(UserEntity.of(request)));
    }

    @Override
    public UserData update(Long id, UserRequest request) {
        var data = userRepository.findById(id).map(it -> {
            var firstName = Optional.of(request.getFirstName()).orElse(it.getFirstName());
            var lastName = Optional.of(request.getLastName()).orElse(it.getLastName());
            var email = Optional.of(request.getEmail()).orElse(it.getEmail());
            var passwordHash = Optional.of(request.getPasswordHash()).orElse(it.getPasswordHash());
            return userRepository.save(new UserEntity(id, firstName, lastName, email, passwordHash));
        }).orElse(null);
        //Catch exception.
        if (Objects.isNull(data)) throw new IllegalArgumentException();
        return toData(data);
    }

    @Override
    public Set<UserItem> fetchAll() {
        return StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .filter(Objects::nonNull)
                .map(this::toItem)
                .collect(Collectors.toSet());
    }

    @Override
    public UserData fetchById(Long id) {
        var entity = userRepository.findById(id);
        if (entity.isPresent()) {
            var data = entity.get();
            return toData(data);
        }
        //Catch exception
        throw new IllegalArgumentException();
    }

    @Override
    public boolean deleteById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;

        }
        return false;
    }

    public UserData addTaskByUserId(Long id, TaskData taskData) {
        var user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        var task = new TaskEntity(taskData.getId(), taskData.getTitle(), taskData.getDescription());
        user.addTask(task);
        user = userRepository.save(user);
        return toData(user);
    }

    private UserData toData(UserEntity entity) {
        if (Objects.isNull(entity.getTasks())) entity.setTasks(new ArrayList<>());
        return new UserData(
                 entity.getId(),
                 entity.getFirstName() + " " + entity.getLastName(),
                 entity.getEmail(),
                 entity.getTasks().stream().map(it ->
                         new TaskItem(it.getId(), it.getTitle(), it.getDescription())
                 ).toList()
         );
    }

    private UserItem toItem(UserEntity entity) {
        return new UserItem(
                entity.getId(),
                entity.getEmail()
        );
    }
}
