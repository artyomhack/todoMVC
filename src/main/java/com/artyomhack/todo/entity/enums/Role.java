package com.artyomhack.todo.entity.enums;

public enum Role {

    USER("USER"), ADMIN("ADMIN");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}