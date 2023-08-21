package com.artyomhack.todo.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserData {
    private Long id;
    private String fullName;
    private String email;
}
