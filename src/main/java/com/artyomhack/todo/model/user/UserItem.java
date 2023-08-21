package com.artyomhack.todo.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserItem {
    private Long id;
    private String email;
}
