package org.savea.todoapp.controllers;

import org.savea.todoapp.config.DiffUtil.Change;

import java.util.Date;
import java.util.Map;

public record ActivityDto(
    long revisionId,
    Date timestamp,
    String username,
    Map<String, Change<?>> diff
) {}
