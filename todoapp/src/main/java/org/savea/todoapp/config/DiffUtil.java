package org.savea.todoapp.config;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class DiffUtil{

    /**
     * Computes the differences between two objects by comparing the values of their fields.
     * Only fields with different values are included in the resulting map.
     *
     * @param oldObj the original object to compare
     * @param newObj the updated object to compare
     * @return a map where the keys are field names and the values are {@code Change<?>} objects representing
     *         the old and new values of the fields that have changed
     * @throws IllegalAccessException if the fields of the objects are not accessible
     */
    public static Map<String, Change<?>> diff(Object oldObj, Object newObj) throws IllegalAccessException {
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

