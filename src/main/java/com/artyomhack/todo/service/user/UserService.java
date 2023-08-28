package com.artyomhack.todo.service.user;

import com.artyomhack.todo.entity.UserEntity;
import com.artyomhack.todo.model.task.TaskRequest;
import com.artyomhack.todo.model.user.UserData;
import com.artyomhack.todo.model.user.UserItem;
import com.artyomhack.todo.model.user.UserRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public interface UserService {
    UserData create(UserRequest request);
    UserData update(Long id, UserRequest request);
    Set<UserItem> fetchAll();
    UserData fetchById(Long id);
    boolean deleteById(Long id);
    UserData addTaskByUserId(Long id, TaskRequest taskRequest);
    void deleteTaskByIdFromUserById(Long taskId, Long userId);
    boolean existsByEmail(String email);
    UserData loadByEmail(String email);
}
