package org.savea.todoapp.models;

import lombok.Getter;

@Getter
public enum Status {
    PENDING("Pending", "Task is pending"),
    IN_PROGRESS("In Progress", "Task is currently being worked on"),
    COMPLETED("Completed", "Task has been completed"),
    ON_HOLD("On Hold", "Task is on hold"),
    CANCELLED("Cancelled", "Task has been cancelled");

    private final String name;
    private final String description;

    Status(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static Status getEnumObject(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        for (Status status : Status.values()) {
            if (status.getName().equalsIgnoreCase(value)) {
                return status;
            }
        }
        return null;
    }
}
