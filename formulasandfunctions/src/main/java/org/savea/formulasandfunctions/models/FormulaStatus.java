package org.savea.formulasandfunctions.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@AllArgsConstructor
public enum FormulaStatus {

    ACTIVE("Active", "The formula is active"),
    INACTIVE("Inactive", "The formula is inactive");

    private final String name;
    private final String description;

    public static FormulaStatus getEnumObject(String value) {
        if (StringUtils.isBlank(value))
            return null;
        for (FormulaStatus object : FormulaStatus.values()) {
            if (object.getName().equals(value) || object.getDescription().equals(value))
                return object;
        }
        return null;
    }

    @Override
    public String toString() {
        return "FormulaStatus{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
