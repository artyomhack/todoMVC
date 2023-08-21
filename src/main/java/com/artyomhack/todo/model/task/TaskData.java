package com.artyomhack.todo.model.task;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskData {
    private Long id;
    private String title;
    private String description;
}
