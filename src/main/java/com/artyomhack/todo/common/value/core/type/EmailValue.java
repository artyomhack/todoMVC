package com.artyomhack.todo.common.value.core.type;

import com.artyomhack.todo.common.value.core.ValueType;

public class EmailValue implements ValueType<String> {

    private final String value;

    public EmailValue(String value) {
        this.value = value; // will be add method isValid()
    }
//
//    private static EmailValue of(String value) {
//
//    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public boolean isEmpty() {
        return getValue().isEmpty();
    }
}
