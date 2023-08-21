package com.artyomhack.todo.model.user;

import com.artyomhack.todo.model.task.TaskItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserRequest {
    private Long id;
    private String fullName;
    private String email;
    private String passwordHash;
    private List<TaskItem> tasks;
}
