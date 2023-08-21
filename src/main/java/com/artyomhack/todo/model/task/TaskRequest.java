package com.artyomhack.todo.model.task;

import com.artyomhack.todo.model.user.UserItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class TaskRequest {
    private Long id;
    private String title;
    private String description;
    private Set<UserItem> users;
}
