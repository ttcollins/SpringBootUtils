package org.savea.todoapp.config;

public record Change<T>(T oldValue, T newValue) {}
