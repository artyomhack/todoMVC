package com.artyomhack.todo.model.user;

import com.artyomhack.todo.model.task.TaskItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserData {
    private Long id;
    private String fullName;
    private String email;
    private List<TaskItem> tasks;
}
