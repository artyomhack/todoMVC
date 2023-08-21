package com.artyomhack.todo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "usr")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usr_task",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "usr_id")
    )
    private List<TaskEntity> tasks = new ArrayList<>();

    public UserEntity(Long id, String firstName, String lastName, String number, List<TaskEntity> tasks) {
        this(id, firstName, lastName, number);
        this.tasks = tasks;
    }

    public UserEntity(Long id, String firstName, String lastName, String email, String passwordHash) {
        this(id, firstName, lastName, email);
        this.passwordHash = passwordHash;
    }

    public UserEntity(Long id, String firstName, String lastName, String email) {
        this(id, firstName, lastName);
        this.email = email;
    }

    public UserEntity(Long id, String firstName, String lastName) {
        this(id, firstName);
        this.lastName = lastName;
    }

    public UserEntity(Long id, String firstName) {
        this(id);
        this.firstName = firstName;
    }

    public UserEntity(Long id) {
        this.id = id;
    }

    public UserEntity() {
    }
}
