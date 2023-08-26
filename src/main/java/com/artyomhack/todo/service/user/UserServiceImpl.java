package com.artyomhack.todo.service.user;

import com.artyomhack.todo.entity.TaskEntity;
import com.artyomhack.todo.entity.UserEntity;
import com.artyomhack.todo.model.task.TaskItem;
import com.artyomhack.todo.model.task.TaskRequest;
import com.artyomhack.todo.model.user.UserData;
import com.artyomhack.todo.model.user.UserItem;
import com.artyomhack.todo.model.user.UserRequest;
import com.artyomhack.todo.repository.TaskRepository;
import com.artyomhack.todo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, TaskRepository taskRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserData create(UserRequest request) {
        request.setPasswordHash(passwordEncoder.encode(request.getPasswordHash()));
        System.out.println(request);
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

    public UserData addTaskByUserId(Long userId, TaskRequest taskRequest) {
        return userRepository.findById(userId).map(it -> {
            var task = taskRepository.save(TaskEntity.of(taskRequest));
            it.addTask(task);
            return toData(userRepository.save(it));
        }).orElseThrow(IllegalArgumentException::new);
    }

    public void deleteTaskByIdFromUserById(Long taskId, Long userId) {
        var user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        var task = taskRepository.findById(taskId).orElseThrow(IllegalArgumentException::new);

        user.removeTask(task);
        userRepository.save(user);

        taskRepository.deleteById(task.getId());
    }

    @Override
    public UserData findByUsername(String email) {
        return null;
    }

    @Override
    public boolean existsByEmail(String email) {
      return StreamSupport
              .stream(userRepository.findAll().spliterator(), false)
              .anyMatch(it -> it.getEmail().contains(email));
    }


    private UserData toData(UserEntity entity) {
        return new UserData(
                 entity.getId(),
                 entity.getFirstName() + " " + entity.getLastName(),
                 entity.getEmail(),
                 entity.getTasks().stream().map(it ->
                         new TaskItem(it.getId(), it.getTitle(),
                                      it.getDescription(), it.getDate())
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
