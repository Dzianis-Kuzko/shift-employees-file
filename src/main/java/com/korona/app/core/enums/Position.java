package com.korona.app.core.enums;

public enum Position {
    EMPLOYEE("Employee"),
    MANAGER("Manager");

    private final String value;

    Position(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
