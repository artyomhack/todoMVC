package com.artyomhack.todo.model.task;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskItem {
    private Long id;
    private String title;
    private String description;
}
