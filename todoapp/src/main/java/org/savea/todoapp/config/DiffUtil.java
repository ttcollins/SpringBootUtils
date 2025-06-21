package org.savea.todoapp.config;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Objects;

public class DiffUtil{

    public static Map<String, Change<?>> diff(Object oldObj, Object newObj) {
        Map<String, Change<?>> changes = new LinkedHashMap<>();
        for (Field f : oldObj.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            Object oldVal = f.get(oldObj);
            Object newVal = f.get(newObj);
            if (!Objects.equals(oldVal, newVal))
                changes.put(f.getName(), new Change<>(oldVal, newVal));
        }
        return changes;
    }
}

