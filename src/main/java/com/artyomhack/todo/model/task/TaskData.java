package com.artyomhack.todo.model.task;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
public class TaskData {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime date;
}
