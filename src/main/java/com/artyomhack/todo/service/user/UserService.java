package com.artyomhack.todo.service.user;

import com.artyomhack.todo.entity.TaskEntity;
import com.artyomhack.todo.entity.UserEntity;
import com.artyomhack.todo.model.task.TaskItem;
import com.artyomhack.todo.model.task.TaskRequest;
import com.artyomhack.todo.model.user.UserData;
import com.artyomhack.todo.model.user.UserItem;
import com.artyomhack.todo.model.user.UserRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface UserService {
    UserData create(UserRequest request);
    UserData update(Long id, UserRequest request);
    Set<UserItem> fetchAll();
    UserData fetchById(Long id);
    boolean deleteById(Long id);
}
