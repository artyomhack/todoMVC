package com.artyomhack.todo.service.user;

import com.artyomhack.todo.entity.TaskEntity;
import com.artyomhack.todo.entity.UserEntity;
import com.artyomhack.todo.model.task.TaskData;
import com.artyomhack.todo.model.task.TaskItem;
import com.artyomhack.todo.model.task.TaskRequest;
import com.artyomhack.todo.model.user.UserData;
import com.artyomhack.todo.model.user.UserItem;
import com.artyomhack.todo.model.user.UserRequest;
import com.artyomhack.todo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserData create(UserRequest request) {
        return toData(userRepository.save(convertFromRequest(request)));
    }

    @Override
    public UserData update(Long id, UserRequest request) {
        var data = userRepository.findById(id).map(it -> {
            var firstName = Optional.of(request.getFullName().split(" ")[0]).orElse(it.getFirstName());
            var lastName = Optional.of(request.getFullName().split(" ")[1]).orElse(it.getLastName());
            var email = Optional.of(request.getEmail()).orElse(it.getEmail());
            var passwordHash = Optional.of(request.getPasswordHash()).orElse(it.getPasswordHash());
            return new UserEntity(id, firstName, lastName, email, passwordHash);
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

//    private UserEntity convertFromRequest(UserRequest request) {
//        var firstName = request.getFullName().split(" ")[0];
//        var lastName = request.getFullName().split(" ")[1];
//        if (Objects.isNull(request.getTasks())) request.setTasks(new ArrayList<>());
//        return new UserEntity(
//                request.getId(),
//                firstName,
//                lastName,
//                request.getNumber(),
//                request.getTasks().stream()
//                        .map(it -> new TaskEntity(it.getId(), it.getTitle(),it.getDescription()))
//                        .toList()
//        );
//    }
//
//    private UserData toData(UserEntity entity) {
//        return new UserData(
//                entity.getId(),
//                entity.getFirstName() + " " + entity.getLastName(),
//                entity.getNumber()
//        );
//    }
//
//    private UserItem toItem(UserEntity entity) {
//        return new UserItem(
//                entity.getId(),
//                entity.getLastName() + " " + entity.getLastName(),
//                entity.getNumber()
//        );
//    }
}
