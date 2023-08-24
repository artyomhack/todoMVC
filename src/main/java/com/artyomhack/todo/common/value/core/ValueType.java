package com.artyomhack.todo.common.value.core;

public interface ValueType<T> {

    T getValue();

    boolean isEmpty();
}
