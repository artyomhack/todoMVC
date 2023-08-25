package com.artyomhack.todo.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserItem {
    private Long id;
    private String email;
}
