package com.artyomhack.todo.entity;

import com.artyomhack.todo.common.value.core.type.EmailValue;
import com.artyomhack.todo.model.user.UserRequest;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "usr")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private EmailValue email;
    private String passwordHash;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usr_task",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "usr_id")
    )
    private List<TaskEntity> tasks = new ArrayList<>();

    public UserEntity(Long id, String firstName, String lastName, EmailValue email, String passwordHash, List<TaskEntity> tasks) {
        this(id, firstName, lastName, email, passwordHash);
        this.tasks = tasks;
    }

    public UserEntity(Long id, String firstName, String lastName, EmailValue email, String passwordHash) {
        this(id, firstName, lastName, email);
        this.passwordHash = passwordHash;
    }

    public UserEntity(Long id, String firstName, String lastName, EmailValue email) {
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

    public static UserEntity of(UserRequest request) {
        if (Objects.isNull(request.getTasks())) request.setTasks(new ArrayList<>());
        return new UserEntity(
                request.getId(),
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPasswordHash(),
                request.getTasks().stream().map(it ->
                        new TaskEntity(it.getId(), it.getTitle(), it.getDescription())
                ).toList()
        );
    }

    public void addTask(TaskEntity entity) {
        this.tasks.add(entity);
    }

    public List<TaskEntity> removeTask(TaskEntity entity) {
        if (!this.tasks.isEmpty()) tasks.remove(entity);
        return this.tasks;
    }
}
