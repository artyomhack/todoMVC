package com.artyomhack.todo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "task")
@Data
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usr_task",
            joinColumns = @JoinColumn(name = "usr_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    private Set<UserEntity> users = new HashSet<>();

    public TaskEntity(Long id, String title, String description, Set<UserEntity> users) {
        this(id, title, description);
        this.users = users;
    }

    public TaskEntity(Long id, String title, String description) {
        this(id, title);
        this.description = description;
    }

    public TaskEntity(Long id, String title) {
        this(id);
        this.title = title;
    }

    public TaskEntity(Long id) {
        this.id = id;
    }

    public TaskEntity() {

    }

}
